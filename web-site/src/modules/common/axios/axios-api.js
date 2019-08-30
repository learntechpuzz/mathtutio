import axios from 'axios';

const instance = axios.create({
      baseURL: 'https://opnw5ozyi1.execute-api.us-east-1.amazonaws.com/dev'
    //baseURL: 'APIGateway-URL'
});

export default instance;
