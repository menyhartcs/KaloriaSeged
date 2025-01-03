import axios from "axios";

const axiosClient = axios.create({
    baseURL: 'http://localhost:8080',
    withCredentials: true
});

const REST_API_BASE_URL = '/food';

export const listFoods = () => axiosClient.get(REST_API_BASE_URL);
export const createFood = (food) => axiosClient.post(REST_API_BASE_URL, food);
export const getFoodById = (foodId) => axiosClient.get(REST_API_BASE_URL + "/id/" + foodId);
export const getFoodByName = (name) => axiosClient.get(REST_API_BASE_URL + "/name/" + name);
export const updateFood = (foodId, food) => axiosClient.put(REST_API_BASE_URL + "/" + foodId, food);
export const deleteFood = (foodId) => axiosClient.delete(REST_API_BASE_URL + "/" + foodId);