#!/bin/bash

# Compilar interfase remoto (MessagingAppRMI.java)
echo "javac MessagingAppRMI.java"
javac MessagingAppRMI.java

# Compilar implementación interfase remoto (MessagingAppRMIServant.java)
echo "javac MessagingAppRMIServant.java" 
javac MessagingAppRMIServant.java

# Generar skeleton y stubs para el servidor y el cliente (MessagingAppRMIServant_Stub.class Jdk<1.5 and MessagingAppRMIServant_Skeleton.class Jdk<1.2)
echo "rmic -vcompat MessagingAppRMIServant"
rmic -vcompat MessagingAppRMIServant

# Compilar Servidor (MessagingAppRMIServer.java)
echo "javac MessagingAppRMIServer.java"
javac MessagingAppRMIServer.java

# Compilar cliente (MessagingAppRMIClient.java)
echo "javac MessagingAppRMIClient.java"
javac MessagingAppRMIClient.java
