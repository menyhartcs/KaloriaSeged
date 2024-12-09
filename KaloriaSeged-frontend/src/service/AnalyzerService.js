import axios from "axios";

const axiosClient = axios.create({
    baseURL: 'http://localhost:8080',
    withCredentials: true
});

const REST_API_BASE_URL = '/complete';

export const analyze = (prompt) => axiosClient.post(REST_API_BASE_URL, { prompt })