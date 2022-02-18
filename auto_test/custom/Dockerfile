FROM alpine:3.14

COPY entrypoint.sh /
COPY test1.js /

RUN apk add firefox \
    && apk add --update nodejs npm \
    && npm install -g testcafe \
    && chmod u+x entrypoint.sh

ENTRYPOINT [ "./entrypoint.sh" ]
