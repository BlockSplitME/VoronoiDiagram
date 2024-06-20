FROM node:lts-alpine

RUN npm install -g http-server

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build

EXPOSE 3000
CMD [ "http-server", "dist", "-p", "3000" ]

#docker run -it -p 8080:8080 voronov-diagram
# docker build -t voronov-diagram .