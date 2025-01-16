terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = var.region
  access_key = var.access_key
  secret_key = var.secret_key
}

resource "aws_ecr_repository" "example_ecr" {
  name                 = "my-app-repo"
}

resource "aws_iam_role" "example_role" {
  name = "role-prueba-backend-tf"

  assume_role_policy = <<EOF
    {
    "Version": "2012-10-17",
    "Statement": [
        {
        "Effect": "Allow",
        "Principal": {
            "Service": "ecs-tasks.amazonaws.com"
        },
        "Action": "sts:AssumeRole"
        }
    ]
    }
    EOF
}

resource "aws_iam_role_policy_attachment" "attach_ecs_task_execution_policy" {
  role       = aws_iam_role.example_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

resource "aws_iam_role_policy_attachment" "attach_cloudwatch_policy" {
  role       = aws_iam_role.example_role.name
  policy_arn = "arn:aws:iam::aws:policy/CloudWatchLogsFullAccess"
}

resource "aws_ecs_task_definition" "example_taskdef" {
  family = "taskdef-prueba-backend-tf"
  network_mode = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  execution_role_arn = aws_iam_role.example_role.arn
  task_role_arn = aws_iam_role.example_role.arn
  cpu = 1024
  memory = 3072
  container_definitions = jsonencode([
    {
      name      = "container-prueba-backen-tf"
      image     = aws_ecr_repository.example_ecr.repository_url
      essential = true
      portMappings = [
        {
          containerPort = 8080
          hostPort = 8080
          name = "port-prueba-backend-tf"
          appProtocol = "http"
        }
      ]
      logConfiguration = {
        logDriver = "awslogs"
        options = {
            awslogs-create-group  = "true"
            awslogs-group         = "/ecs/container-prueba-backen-tf"   
            mode                  = "non-blocking"
            max-buffer-size       = "25m"     
            awslogs-region        = var.region                    
            awslogs-stream-prefix = "ecs"                       
        }
      }
    }
  ])
}

resource "aws_ecs_cluster" "example_cluster" {
  name = "cluster-prueba-backend-tf"
}

resource "aws_lb" "example_lb" {
  name               = "lb-prueba-back"
  internal           = false
  load_balancer_type = "application"
  security_groups    = ["sg-009e403e14f5dd18d"]
  subnets            = ["subnet-0d0c3f17b63ca82c3","subnet-0e60234f27ee17ac0","subnet-0995ff654a7b6c34a"]
}

resource "aws_lb_target_group" "example_tg" {
  name        = "target-group-prueba-backend"
  port        = 80
  protocol    = "HTTP"
  vpc_id      = "vpc-09911698cab14a322"
  target_type = "ip"

  health_check {
    interval            = 30 
    path                = "/api/producto/mayor-stock/franquicia/1000"  
    timeout             = 5  
    healthy_threshold   = 3  
    unhealthy_threshold = 2 
    matcher             = "200"
  }
}

resource "aws_lb_listener" "example_listener" {
  load_balancer_arn = aws_lb.example_lb.arn
  port              = 80
  protocol          = "HTTP"
  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.example_tg.arn
  }
}

resource "aws_ecs_service" "example_service" {
  name            = "service-prueba-backen-tf"
  cluster         = aws_ecs_cluster.example_cluster.id
  task_definition = aws_ecs_task_definition.example_taskdef.arn
  desired_count   = 1
  launch_type     = "FARGATE"

  network_configuration {
    subnets         = ["subnet-0d0c3f17b63ca82c3","subnet-0e60234f27ee17ac0","subnet-0995ff654a7b6c34a"]
    security_groups = ["sg-009e403e14f5dd18d"]
    assign_public_ip = true
  }

  load_balancer {
    target_group_arn = aws_lb_target_group.example_tg.arn
    container_name   = "container-prueba-backen-tf"
    container_port   = 8080         
  }

  depends_on = [aws_lb_listener.example_listener]
}

resource "aws_api_gateway_rest_api" "example_api" {
  name        = "api-gateway-prueba-backend"
  description = "API Gateway para la aplicación ECS"
}

resource "aws_api_gateway_resource" "example_resource" {
  rest_api_id = aws_api_gateway_rest_api.example_api.id
  parent_id   = aws_api_gateway_rest_api.example_api.root_resource_id
  path_part   = "api"
}

resource "aws_api_gateway_method" "example_method" {
  rest_api_id   = aws_api_gateway_rest_api.example_api.id
  resource_id   = aws_api_gateway_resource.example_resource.id
  http_method   = "ANY"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "example_integration" {
  rest_api_id             = aws_api_gateway_rest_api.example_api.id
  resource_id             = aws_api_gateway_resource.example_resource.id
  http_method             = aws_api_gateway_method.example_method.http_method
  type                    = "HTTP_PROXY"
  integration_http_method = "ANY"
  uri                     = "http://${aws_lb.example_lb.dns_name}"
}

resource "aws_api_gateway_deployment" "example" {
  rest_api_id = aws_api_gateway_rest_api.example_api.id
  triggers = {
    redeployment = sha1(jsonencode(aws_api_gateway_rest_api.example_api))
  }

  depends_on = [
    aws_api_gateway_method.example_method,
    aws_api_gateway_integration.example_integration
  ]
}

resource "aws_api_gateway_stage" "example_stage" {
  stage_name    = "prod"
  rest_api_id   = aws_api_gateway_rest_api.example_api.id
  deployment_id = aws_api_gateway_deployment.example.id

  description = "Production stage"
}

output "api_gateway_url" {
  value = "http://${aws_lb.example_lb.dns_name}"
  description = "URL del punto de enlace"
}