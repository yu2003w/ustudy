FROM nginx:1.12

# set timezone
ENV TIME_ZONE=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TIME_ZONE /etc/localtime && echo $TIME_ZONE > /etc/timezone

# settings for https certification
RUN mkdir /etc/nginx/cert
COPY nginx.conf /etc/nginx/
COPY cert/214555228090077.pem /etc/nginx/cert/
COPY cert/214555228090077.key /etc/nginx/cert/

EXPOSE	80 443
