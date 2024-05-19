import React, {useEffect, useState} from 'react'
import {deleteUser, listUsers} from "../service/UserService.js";
import {useNavigate} from "react-router-dom";

const ListUserComponent = () => {

    const [users, setUsers] = useState([])

    const navigator = useNavigate();

    useEffect(() => {
        getAllUsers();
    }, []);

    function getAllUsers() {
        listUsers().then((response) => {
            setUsers(response.data);
        }).catch(error => {
            console.error(error);
        })
    }

    function addNewUser() {
        navigator("/add-user")
    }

    function updateUser(id) {
        navigator(`/edit-user/${id}`)
    }

    function removeUser(id) {
        console.log(id);

        deleteUser(id).then((response) => {
            getAllUsers();
        }).catch(error => {
            console.error(error);
        })
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
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {
                    users.map(user =>
                        <tr key={user.id}>
                            <td>{user.id}</td>
                            <td>{user.name}</td>
                            <td>{user.email}</td>
                            <td>
                                <button className="btn btn-info" onClick={() => updateUser(user.id)}>Update</button>
                                <button className="btn btn-danger" onClick={() => removeUser(user.id)}
                                style={{marginLeft: "10px"}}>Delete</button>
                            </td>
                        </tr>)
                }
                </tbody>
            </table>
        </div>
    )
}

export default ListUserComponent
