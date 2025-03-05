#Build the frontend
FROM node:20.9.0 as build-stage

WORKDIR /home/node/app

COPY package*.json yarn.lock ./

RUN yarn install

COPY . .

RUN yarn build --preset

#Produce the final container
FROM node:20.9.0 as final

WORKDIR /app/

COPY --from=build-stage /home/node/app/.output ./

EXPOSE 3001

ENV PORT 3001
ENV HOST 0.0.0.0

CMD ["node", "server/index.mjs"]