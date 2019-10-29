### Successfully containerize the backend service.###

[See the backend.Dockerfile in the root folder.](https://github.com/drivexcite/MovieRating/blob/master/backend.Dockerfile)

Most of the complexity was to get the pom.xml configuration for Spring Boot maven plugin.


[See the fronend.Dockerfile in the root folder.](https://github.com/drivexcite/MovieRating/blob/master/frontend.Dockerfile)

The biggest challenge was to forward calls form the server-side of the front end project to the Java backend.

### Create a namespace in the kubernetes cluster.###
I did it declaratively. [See the 00.namespaces.yaml in the deployment folder.](https://github.com/drivexcite/MovieRating/blob/master/deployment/00.namespaces.yaml)


### Install the backend service into the kubernetes cluster.###
[See the 02.movierating-backend.deployment.yaml in the deployment folder.](https://github.com/drivexcite/MovieRating/blob/master/deployment/02.movierating-backend.deployment.yaml)


### Install the frontend app into the kubernetes cluster.###
[See the 03.movierating-frontend.deployment.yaml in the deployment folder.](https://github.com/drivexcite/MovieRating/blob/master/deployment/03.movierating-frontend.deployment.yaml)

### Frontend app successfully calls the backend service within the cluster.###
It should be doing it right now. [If the cluster is alive, go here to test the app.](http://13.88.176.196/)
![MovieRatingApp](https://github.com/drivexcite/MovieRating/tree/master/images/MovieRatingApp.jpg)

### Create an ingress into the cluster for the front end only.###
[See the 03.movierating-frontend.deployment.yaml in the deployment folder.](https://github.com/drivexcite/MovieRating/blob/master/deployment/03.movierating-frontend.deployment.yaml)

The relevant section is from line 44 onwards. It's the last resource:
```yaml
apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  name: movierating-ingress
  namespace: tona-ns
  annotations:
    prometheus.io/scrape: "true"
    prometheus.io/port: "9100"
spec:
  rules:
  - http:
      paths:
      - path: /
        backend:
          serviceName: movierating-frontend
		  servicePort: 9090
```
### Create an Horizontal Pod Auto-scaler in the cluster, using cpu utilization as the driving metric.###
[See the 04.horizontal-pod-autoscaler-v2.yaml in the deployment folder.](https://github.com/drivexcite/MovieRating/blob/master/deployment/04.horizontal-pod-autoscaler-v2.yaml)

### Screenshot of node information from the console.###
![kubectl get nodes](https://github.com/drivexcite/MovieRating/tree/master/images/kubectl-get-nodes.jpg)

### Screenshot of pod information for your namespace only.###
![kubectl get pods -n tona-ns](https://github.com/drivexcite/MovieRating/tree/master/images/kubectl-get-pods.jpg)

### Screenshot of console logs from your backend service.###
![kubectl logs --follow backend -n tona-ns](https://github.com/drivexcite/MovieRating/tree/master/images/kubectl-logs-follow-backend.jpg)

### Screenshot of console logs from your frontend application.###
![kubectl logs --follow frontend -n tona-ns](https://github.com/drivexcite/MovieRating/tree/master/images/kubectl-logs-follow-frontend.jpg)

### Install Prometheus into the cluster.###
[See the 05.prometheus.yaml in the deployment folder.](https://github.com/drivexcite/MovieRating/blob/master/deployment/05.prometheus.yaml)
![Prometheus (targets)](https://github.com/drivexcite/MovieRating/tree/master/images/PrometheusTargets.jpg)
![Prometheus (rules)](https://github.com/drivexcite/MovieRating/tree/master/images/PrometheusRules.jpg)

### Install Grafana into the cluster.###
[See the 06.grafana.yaml in the deployment folder.](https://github.com/drivexcite/MovieRating/blob/master/deployment/06.grafana.yaml)
![Grafana](https://github.com/drivexcite/MovieRating/tree/master/images/Grafana.jpg)

### Install Istio into the cluster.###
[See the 07.istio.yaml in the deployment folder.](https://github.com/drivexcite/MovieRating/blob/master/deployment/07.istio.yaml)
![Istio Installed](https://github.com/drivexcite/MovieRating/tree/master/images/IstioInstalled01.jpg)
![Istio Services](https://github.com/drivexcite/MovieRating/tree/master/images/IstioInstalled02.jpg)

It's actually a relief that the requirement was only to install it and not configure it, because it's a damn nightmare. Needless to say, the installation YAML was created using Helm.

### Tear down your deployment and re-deploy.###
![kubectl delete](https://github.com/drivexcite/MovieRating/tree/master/images/kubectl-delete.jpg)
![kubectl apply](https://github.com/drivexcite/MovieRating/tree/master/images/kubectl-apply.jpg)
![kubectl get pods](https://github.com/drivexcite/MovieRating/tree/master/images/kubectl-get-pods-after-recreate.jpg)

### Get a pod stuck in a pending state, and show how to resolve it.#
This is not even funny. During the long long phase of configuring Prometheus, I was having a problem. For some reason, the prometheus-server node was taking too long to initialize, and after a couple of minutes I found it was on a ![CrashLoopBackoff](https://github.com/drivexcite/MovieRating/tree/master/images/ProblemSolving1.jpg). But then, I didn't really know how to diagnose Pod problems that were related to a bad resource definition, which is a pretty simple mistake to make when working with YAML. So after some time I discovered that kubectl has a ![describe](https://github.com/drivexcite/MovieRating/tree/master/images/ProblemSolving2.jpg) command that lets you instrospect into the inner workings of the Pod scheduling process. Sure enough, at the very end of the ![describe output](https://github.com/drivexcite/MovieRating/tree/master/images/ProblemSolving3.jpg), there was an error related to configuration, in this case, I mounted a volume in the wrong path (![see the highlited lines](https://github.com/drivexcite/MovieRating/tree/master/images/ProblemSolving4.jpg) in the prometheus definition file) and thus without the prometheus.yaml configuration file available for the container, it would never start. So went back to the YAML and change the mount point from etc/prometheus to etc/config and reapplied the resource. After a while, ![I described](https://github.com/drivexcite/MovieRating/tree/master/images/ProblemSolving5.jpg) the Pod once again and I could no longer see ![any errors in the output](https://github.com/drivexcite/MovieRating/tree/master/images/ProblemSolving6.jpg) and the Prometheus Server ![finally fricking worked](https://github.com/drivexcite/MovieRating/tree/master/images/ProblemSolving6.jpg)!