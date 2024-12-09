import axios from "axios";

const axiosClient = axios.create({
    baseURL: 'http://localhost:8080',
    withCredentials: true
});

const REST_API_BASE_URL = '/exercise';

export const listExercises = () => axiosClient.get(REST_API_BASE_URL);
export const createExercise = (exercise) => axiosClient.post(REST_API_BASE_URL, exercise);
export const getExerciseById = (exerciseId) => axiosClient.get(REST_API_BASE_URL + "/id/" + exerciseId);
export const getExerciseByName = (name) => axiosClient.get(REST_API_BASE_URL + "/name/" + name);
export const updateExercise = (exerciseId, exercise) => axiosClient.put(REST_API_BASE_URL + "/" + exerciseId, exercise);
export const deleteExercise = (exerciseId) => axiosClient.delete(REST_API_BASE_URL + "/" + exerciseId);