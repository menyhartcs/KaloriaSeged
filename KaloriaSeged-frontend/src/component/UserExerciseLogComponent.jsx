import React, {useEffect, useState} from "react";
import {useLocation, useNavigate, useParams} from "react-router-dom";
import {createUserExerciseLog, getUserExerciseLog, updateUserExerciseLog} from "../service/UserExerciseLogService.js";
import moment from "moment/moment.js";
import {checkLoginStatus, getUserByEmail} from "../service/UserService.js";
import {getExerciseById} from "../service/ExerciseService.js";

const UserExerciseLogComponent = () => {

    const [User, setUser] = useState({})
    const [Exercise, setExercise] = useState({})
    const [date, setDate] = useState(getCurrentDate)
    const [duration, setDuration] = useState(10)
    const location = useLocation();
    const currentUrl = location.pathname.split('/')[1]
    const navigator = useNavigate();
    const {id} = useParams();
    const durationMultiplier =  duration;
    const currentEmail = localStorage.getItem("email")

    useEffect(() => {
        checkLoginStatus(currentEmail).then(() => {
        }).catch(() => {
            console.log("ISMERETLEN FELHASZNÁLÓ")
            navigator("/UserLogIn");
        })
    }, []);

    const [errors, setErrors] = useState({
        User: "",
        Exercise: "",
        date: "",
        duration: ""
    })

    useEffect(() =>{
        if (currentUrl === "edit-userExerciseLog") {
            getUserExerciseLog(id).then((response) => {
                const userData = response.data.user || {};
                const exerciseData = response.data.exercise || {};
                const logDate = response.data.date || getCurrentDate();
                const logDuration = response.data.duration || {};

                setUser(userData);
                setExercise(exerciseData);
                setDate(logDate);
                setDuration(logDuration);
            }).catch(error => {
                console.error(error)
            })
        }
    }, [id])

    let email = localStorage.getItem("email")
    useEffect(() =>{
        if (currentUrl === "add-userExerciseLog") {
            getExerciseById(id).then((response) => {
                setExercise(response.data)
            }).catch(error => {
                console.error(error)
            })
            getUserByEmail(email).then((response) => {
                setUser(response.data);
            }).catch(error => {
                console.error(error)
            })
            console.log(User)
            console.log(Exercise)
        }
    }, [])

    function getCurrentDate() {
        return moment().format('YYYY-MM-DD');
    }

    const handleChangeDate = (event) => {
        const newDate = event.target.value;
        setDate(newDate);
    };

    function saveOrUpdateUserExerciseLog(e) {
        e.preventDefault();

        if (validateForm()) {

            const userExerciseLog = {user: User, exercise: Exercise, date, duration}
            console.log(userExerciseLog)

            if (currentUrl === "edit-userExerciseLog") {
                updateUserExerciseLog(id, userExerciseLog).then((response) => {
                    console.log(response.data);
                    navigator("/UserLogs");
                }).catch(error => {
                    console.error(error);
                })
            } else {
                createUserExerciseLog(userExerciseLog).then((response) => {
                    console.log(response.data)
                    navigator("/UserLogs")
                }).catch(error => {
                    console.error(error);
                })
            }

        }

    }

    function validateForm() {
        let valid = true;

        const errorsCopy = {... errors}

        if (User.id) {
            errorsCopy.User = "";
        } else {
            errorsCopy.User = "User id is required!";
            valid = false;
        }

        if (Exercise.id) {
            errorsCopy.Exercise = "";
        } else {
            errorsCopy.Exercise = "Exercise id is required!";
            valid = false;
        }

        setErrors(errorsCopy);

        return valid;
    }

    return (
        <div className="container main-content">
            <br/><br/>
            <div className="row">
                <div className="card col-md-6 offset-md-3 offset-md-3">
                    <h2 className="text-center">Naplóbejegyzés</h2>
                    <div className="card-body">
                        <form>
                            <div className="form-group mb-2">
                                <h4><b>{Exercise.name}</b></h4>
                            </div>

                            <div className="form-group mb-2">
                                <ul>
                                    <li><b className="nutritionText energy">Energia:</b> {Math.round(Exercise.calorie * durationMultiplier)} kcal</li>
                                </ul>
                            </div>

                            <div className="form-group col-md-4 mb-2">
                                <label className="form-label">Időtartam (perc):</label>
                                <input type="number"
                                       name="duration"
                                       value={duration}
                                       className={`form-control ${errors.duration ? "is-invalid" : ""}`}
                                       onChange={(e) => setDuration(e.target.value)}
                                />
                                {errors.duration && <div className="invalid-feedback">{errors.duration}</div>}
                            </div>

                            <div className="mb-3">
                                <label htmlFor="dateInput" className="form-label">Válaszd ki melyik nap végezted:</label>
                                <input
                                    type="date"
                                    id="dateInput"
                                    className="form-control"
                                    value={date || getCurrentDate()}
                                    onChange={handleChangeDate}
                                />
                            </div>

                            <button className="btn btn-success" onClick={saveOrUpdateUserExerciseLog}>Mentés</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default UserExerciseLogComponent