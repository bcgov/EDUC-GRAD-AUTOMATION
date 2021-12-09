FROM debian

WORKDIR /usr/src/app

#ENV ALPINE_REPOS="\
# --repository http://dl-cdn.alpinelinux.org/alpine/edge/testing/\
# --repository http://dl-cdn.alpinelinux.org/alpine/edge/community/\
# --repository http://dl-cdn.alpinelinux.org/alpine/edge/main/\
# --repository http://dl-cdn.alpinelinux.org/alpine/v3.11/community/\
# --repository http://dl-cdn.alpinelinux.org/alpine/v3.11/main/\
#"
ENV GIT_REPO="https://github.com/bcgov/EDUC-GRAD-TEST-AUTOMATION.git"
ENV TEST_ADMIN_USERNAME=""
ENV TEST_ADMIN_PASSWORD=""
ENV TEST_PEN=""
ENV BASE_URL=""

COPY run-tests.sh .

#RUN apk --no-cache $ALPINE_REPOS upgrade && \
 #apk --no-cache $ALPINE_REPOS add \
 #libevent git bash nodejs npm chromium firefox xwininfo xvfb dbus eudev ttf-freefont fluxbox procps tzdata && \
 #chmod +x run-tests.sh

# updates and deps
RUN chmod +x run-tests.sh && \
 apt update && \
 apt install git chromium nodejs npm -y

#EXPOSE 1337 1338

ENTRYPOINT ["/bin/bash", "run-tests.sh"]