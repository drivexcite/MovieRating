kubectl get pods --namespace=tona-monitoring-ns
kubectl port-forward prometheus-deployment-56f854c695-4wt4x 8089:9090 --namespace=tona-monitoring-ns