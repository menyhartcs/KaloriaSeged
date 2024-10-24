import axios from "axios";

const axiosClient = axios.create({
    baseURL: 'http://localhost:8080',
});

axiosClient.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});

const REST_API_BASE_URL = '/food';

export const listFoods = () => axiosClient.get(REST_API_BASE_URL);
export const createFood = (food) => axiosClient.post(REST_API_BASE_URL, food);
export const getFood = (foodId) => axiosClient.get(REST_API_BASE_URL + "/" + foodId);
export const updateFood = (foodId, food) => axiosClient.put(REST_API_BASE_URL + "/" + foodId, food);
export const deleteFood = (foodId) => axiosClient.delete(REST_API_BASE_URL + "/" + foodId);