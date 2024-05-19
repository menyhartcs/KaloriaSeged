import React, {useEffect, useState} from 'react'
import {listUsers} from "../service/UserService.js";
import {useNavigate} from "react-router-dom";

const ListUserComponent = () => {

    const [users, setUsers] = useState([])

    const navigator = useNavigate();

    useEffect(() => {
        listUsers().then((response) => {
            setUsers(response.data);
        }).catch(error => {
            console.error(error);
        })
    }, []);

    function addNewUser() {
        navigator("/add-user")
    }

    return (
        <div className="container">
            <h2 className="text-center">List of users</h2>
            <button className="btn btn-dark mb-2" onClick={addNewUser}>Add User</button>
            <table className="table table-striped table-bordered">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Email</th>
                </tr>
                </thead>
                <tbody>
                {
                    users.map(user =>
                        <tr key={user.id}>
                            <td>{user.id}</td>
                            <td>{user.name}</td>
                            <td>{user.email}</td>
                        </tr>)
                }
                </tbody>
            </table>
        </div>
    )
}

export default ListUserComponent
