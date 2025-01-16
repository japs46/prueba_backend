variable "access_key" {
    description = "variable access_key"
    type = string
    sensitive = true
}

variable "secret_key" {
    description = "variable secret_key"
    type = string
    sensitive = true
}

variable "region" {
    description = "La región de AWS donde se desplegarán los recursos"
    type = string
    default     = "us-east-2"
}