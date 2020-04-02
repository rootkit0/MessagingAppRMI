#!/bin/bash

# Compilar interfase remoto (MessagingAppRMI.java)
echo "javac MessagingAppRMI.java"
javac MessagingAppRMI.java

# Compilar implementación interfase remoto (MessagingAppRMIServant.java)
echo "javac MessagingAppRMIServant.java" 
javac MessagingAppRMIServant.java 

# Compilar Servidor (MessagingAppRMIServer.java)
echo "javac MessagingAppRMIServer.java"
javac MessagingAppRMIServer.java

# Compilar cliente (MessagingAppRMIClient.java)
echo "javac MessagingAppRMIClient.java"
javac MessagingAppRMIClient.java
