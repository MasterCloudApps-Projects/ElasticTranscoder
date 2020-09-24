FROM nginx:1.18

RUN rm -rf /usr/share/nginx/html/*
COPY /dist/ /usr/share/nginx/html/

COPY ./nginx/nginx.conf /etc/nginx/conf.d/default.conf


COPY ./entrypoint.sh /
RUN chmod +x entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]

CMD ["nginx", "-g", "daemon off;"]
