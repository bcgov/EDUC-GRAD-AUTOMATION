FROM alpine

WORKDIR /usr/src/app

ENV GIT_REPO="https://github.com/bcgov/EDUC-GRAD-TEST-AUTOMATION.git"
ENV TEST_ADMIN_USERNAME=""
ENV TEST_ADMIN_PASSWORD=""
ENV TEST_PEN=""
ENV BASE_URL=""

COPY run-tests.sh .

# updates and deps
RUN chmod +x run-tests.sh && \
 apk update && \
 apk add git chromium-chromedriver chromium nodejs npm 

ENTRYPOINT ["./run-tests.sh"]