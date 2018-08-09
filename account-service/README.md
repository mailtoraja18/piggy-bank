#account-service micro service

Contains 3 rest apis /customers /accounts and /transactions. Backed by a mongodb database.

### How to Run ?
* Build and run ./mvnw install dockerfile:build
* docker push piggybank/account-service:latest
* kubectl commands:
    * minikube start
    * kubectl create -f k8-local-pv.yaml
    * kubectl create -f k8-local-mongo-service.yaml
    * kubectl apply -f k8-local-mongodb.yaml
    * kubectl apply -f k8-local-web.yaml
    * minikube service web --url
    * use the above url to curl eg : http://192.168.99.100:30214/customers

