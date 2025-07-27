# from é versão da imagem que vou utilizar
FROM alpine:3.21

# executa comandos na etapa de construção
RUN apk add --no-cache openjdk21-jre

# vai copiar algo do host (minha maquina), para container
COPY target/api-diario-oficial.jar /app/api-diario-oficial.jar

# CMD COMANDO QUE SERÁ EXECUTADO DENTRO DO CONTAINER
CMD java -jar /app/api-diario-oficial.jar