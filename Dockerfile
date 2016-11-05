FROM node:6.9

MAINTAINER Julien Smadja <julien.smadja@gmail.com>

RUN mkdir /fightcade
WORKDIR /fightcade

COPY package.json package.json
RUN npm i --production

COPY app app

EXPOSE 8765

CMD [ "npm", "start" ]