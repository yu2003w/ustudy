FROM nginx:1.12

# set timezone
ENV TIME_ZONE=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TIME_ZONE /etc/localtime && echo $TIME_ZONE > /etc/timezone

# settings for https certification
RUN mkdir /etc/nginx/cert
COPY nginx.conf /etc/nginx/
COPY certname.pem /etc/nginx/cert/
COPY certname.key /etc/nginx/cert/

EXPOSE	80 443
