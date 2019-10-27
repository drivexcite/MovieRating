# Imperatively creates an HPA
# https://docs.microsoft.com/bs-cyrl-ba/azure/aks/concepts-scale
kubectl autoscale deployment movierating-frontend --cpu-percent=80 --min=2 --max=8

# Dumps the HPA configuration into a YAML file
kubectl get hpa.v2beta2.autoscaling -o yaml > deployment/horizontal-pod-autoscaler-v2.yaml