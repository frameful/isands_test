#Build the frontend
FROM node:18.20.6-alpine3.21 as build-stage

WORKDIR /home/node/app

COPY package*.json ./

RUN yarn install

COPY . .

RUN yarn build --preset

#Produce the final container
FROM node:18.20.6-alpine3.21 as final

WORKDIR /app/

COPY --from=build-stage /home/node/app/.output ./

EXPOSE 3000

ENV NUXT_HOST=0.0.0.0
ENV NUXT_PORT=3000
CMD ["node", "server/index.mjs"]