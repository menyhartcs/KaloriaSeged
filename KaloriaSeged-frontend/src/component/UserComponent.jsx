import React, {useState} from "react";
import {createUser} from "../service/UserService.js";
import {useNavigate, useParams} from "react-router-dom";

const UserComponent = () => {

    const [name, setName] = useState([])
    const [email, setEmail] = useState([])

    const {id} = useParams();

    const [errors, setErrors] = useState({
        name: "",
        email: "",
    })

    const navigator = useNavigate();

    function saveUser(e) {
        e.preventDefault();

        if (validateForm()) {
            const user = {name, email}
            console.log(user)
            createUser(user).then((response) => {
                console.log(response.data)
                navigator("/users")
            })
        }

    }

    function validateForm() {
        let valid = true;

        const errorsCopy = {... errors}

        if (name.trim) {
            errorsCopy.name = "";
        } else {
            errorsCopy.name = "Name is required!";
            valid = false;
        }

        if (email.trim) {
            errorsCopy.email = "";
        } else {
            errorsCopy.email = "Email address is required!";
            valid = false;
        }

        setErrors(errorsCopy);

        return valid;
    }

    function pageTitle() {
        if (id) {
            return <h2 className="text-center">Update User</h2>
        } else {
            <h2 className="text-center">Add User</h2>
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
                                <label className="form-label">Name:</label>
                                <input type="text"
                                       placeholder="Enter username"
                                       name="name" value={name}
                                       className={`form-control ${errors.name ? "is-invalid" : ""}`}
                                       onChange={(e) => setName(e.target.value)}
                                />
                                { errors.name && <div className="invalid-feedback">{ errors.name }</div> }
                            </div>

                            <div className="form-group mb-2">
                                <label className="form-label">Email:</label>
                                <input type="text"
                                       placeholder="Enter email address"
                                       name="email" value={email}
                                       className={`form-control ${errors.email ? "is-invalid" : ""}`}
                                       onChange={(e) => setEmail(e.target.value)}
                                />
                                { errors.email && <div className="invalid-feedback">{ errors.email }</div> }
                            </div>

                            <button className="btn btn-success" onClick={saveUser}>Submit</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default UserComponent