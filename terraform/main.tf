terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = "us-east-2"
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
            awslogs-region        = "us-east-2"                    
            awslogs-stream-prefix = "ecs"                       
        }
      }
    }
  ])
}

resource "aws_ecs_cluster" "example_cluster" {
  name = "cluster-prueba-backend-tf"
}

resource "aws_ecs_service" "example_service" {
  name            = "service-prueba-backen-tf"
  cluster         = aws_ecs_cluster.example_cluster.id
  task_definition = aws_ecs_task_definition.example_taskdef.arn
  desired_count   = 1
  launch_type     = "FARGATE"
  network_configuration {
    subnets = ["subnet-0d0c3f17b63ca82c3"]
    security_groups = ["sg-009e403e14f5dd18d"]
    assign_public_ip = true
  }
}