FROM node:latest as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY ./ .
RUN npm run build

FROM nginx as production-stage
RUN mkdir /app
ENV backendUrl=http://localhost:8088
COPY --from=build-stage /app/target/dist /app
COPY nginx.conf /etc/nginx/nginx.conf