# Creating an nginx-based ingress deployment.
helm template nginx-ingress --set controller.replicaCount=2 --set controller.nodeSelector."beta\.k ubernetes\.io/os"=linux --set defaultBackend.nodeSelector."beta\.kubernetes\.io/os"=linux > nginx-ingress.deployment.yaml

# Create the front-end, backend and ingress resources:
kubectl create -f .\deployment\movierating-backend.deployment.yaml
kubectl create -f .\deployment\movierating-frontend.deployment.yaml
kubectl create -f .\deployment\nginx-ingress.deployment.yaml

# Make sure the apps are alive and well by inspecting the pod interactively
kubectl exec -it movierating-frontend-6f48bbc85b-xr4sv -- /bin/bash