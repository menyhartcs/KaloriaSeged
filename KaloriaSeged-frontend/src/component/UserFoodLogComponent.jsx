import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {createUserFoodLog, getUserFoodLog, updateUserFoodLog} from "../service/UserFoodLogService.js";
import moment from "moment/moment.js";

const UserFoodLogComponent = () => {

    const [User, setUser] = useState({})
    const [Food, setFood] = useState({})
    const [date, setDate] = useState(getCurrentDate)

    const {id} = useParams();

    const [errors, setErrors] = useState({
        User: "",
        Food: "",
        date: "",
    })

    const navigator = useNavigate();

    useEffect(() =>{
        if (id) {
            getUserFoodLog(id).then((response) => {
                setUser(response.data.User);
                setFood(response.data.Food);
                setDate(response.data.date);
            }).catch(error => {
                console.error(error)
            })
        }

    }, [id])

    function getCurrentDate() {
        return moment().format('YYYY-MM-DD');
    }

    function saveOrUpdateUserFoodLog(e) {
        e.preventDefault();

        if (validateForm()) {

            const userFoodLog = {user: User, food: Food, date}
            console.log(userFoodLog)

            if (id) {
                updateUserFoodLog(id, userFoodLog).then((response) => {
                    console.log(response.data);
                    navigator("/UserFoodLogs");
                }).catch(error => {
                    console.error(error);
                })
            } else {
                createUserFoodLog(userFoodLog).then((response) => {
                    console.log(response.data)
                    navigator("/UserFoodLogs")
                }).catch(error => {
                    console.error(error);
                })
            }

        }

    }

    function validateForm() {
        let valid = true;

        const errorsCopy = {... errors}

        if (User.id && User.id.trim()) {
            errorsCopy.User = "";
        } else {
            errorsCopy.User = "User id is required!";
            valid = false;
        }

        if (Food.id && Food.id.trim()) {
            errorsCopy.Food = "";
        } else {
            errorsCopy.Food = "Food id is required!";
            valid = false;
        }

        setErrors(errorsCopy);

        return valid;
    }

    function pageTitle() {
        if (id) {
            return <h2 className="text-center">Update UserFoodLog</h2>
        } else {
            <h2 className="text-center">Add UserFoodLog</h2>
        }
    }

    return (
        <div className="container">
            <br/><br/>
            <div className="row">
                <div className="card col-md-6 offset-md-3 offset-md-3">
                    {
                        pageTitle()
                    }
                    <div className="card-body">
                        <form>
                            <div className="form-group mb-2">
                                <label className="form-label">User id:</label>
                                <input type="text"
                                       placeholder="Enter user id"
                                       name="User"
                                       value={User.id || ""}
                                       className={`form-control ${errors.User ? "is-invalid" : ""}`}
                                       onChange={(e) => setUser({...User, id: e.target.value})}
                                />
                                {errors.User && <div className="invalid-feedback">{errors.User}</div>}
                            </div>

                            <div className="form-group mb-2">
                                <label className="form-label">Food id:</label>
                                <input type="text"
                                       placeholder="Enter food id"
                                       name="Food"
                                       value={Food.id || ""}
                                       className={`form-control ${errors.Food ? "is-invalid" : ""}`}
                                       onChange={(e) => setFood({...Food, id: e.target.value})}
                                />
                                {errors.Food && <div className="invalid-feedback">{errors.Food}</div>}
                            </div>

                            <button className="btn btn-success" onClick={saveOrUpdateUserFoodLog}>Submit</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default UserFoodLogComponent