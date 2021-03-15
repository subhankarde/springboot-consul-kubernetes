# Spring Boot deployed in Google Kubernetes Engine(GKE)  using Hashicorp Consul for application properties.

### Motivation
- Consul is a better tool for managing application properties rather using ConfigMaps.

### Prerequisites
- Maven
- Docker
- Spring Boot
- Kubernetes(GKE)

### Steps to configure Consul on GKE

- Create a GKE 3 node cluster using Console or CLI
- Add the HashiCorp Helm Chart repository using Cloud Shell
```
helm repo add hashicorp https://helm.releases.hashicorp.com
```
- Create the configuration to override Helm Chart's default
```
# helm-consul-values.yaml
global:
  datacenter: myconsulcluster

ui:
  service:
    type: 'LoadBalancer'

```
- Using Helm 3x install Consul in GKE
```
helm install -f helm-consul-values.yaml hashicorp hashicorp/consul
```
- Once the deployment is completed get the External IP using kubectl and access the Consul UI
- Navigate Consul Key/Value
- Create a Key/Value used by the application, the parent folder should be Config and then the <Service Name>
```
  http://34.121.213.232/ui/myconsulcluster/kv/config/supermanservice/message.greetings/edit
```
![image](https://user-images.githubusercontent.com/14083152/111208456-20b69e80-85a1-11eb-91f2-65b4cff2df89.png)

- Create a Spring Boot app using the above project. Before deploying check the internal Consul Service IP and replace it bootstrap.yaml
- By default, Spring Boot will try to connect to the Consul agent at localhost:8500. To use other settings, we need to update the application.yml/bootstrap.yaml file:
```
spring:
  cloud:
    consul:
      host: <GKE Extenal/Internal IP>
      port: 80
```      
- Using maven build your app. We are using spotify to generate the Docker image. You can also use [Jib](https://cloud.google.com/blog/products/application-development/introducing-jib-build-java-docker-images-better) Maven plugin.
```
mvn clean install -D maven.test.skip=true 
```
- Once the Docker container is built, push it your own repository. The tag name is generated from the POM file
```
docker push subhankarde/supermanservice:<tag name>
```
- Update the deployment.yaml with image name and Consul IP.
- Deploy the app in the cluster
```
kubectl apply -f deployment.yaml
```
- Tear down
```
gcloud container clusters resize <cluster-name> --num-nodes=0 --zone us-central1-c
```
