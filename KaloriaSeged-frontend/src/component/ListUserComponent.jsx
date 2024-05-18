import React, {useEffect, useState} from 'react'
import {listUsers} from "../service/UserService.js";

const ListUserComponent = () => {

    const [users, setUsers] = useState([])

    useEffect(() => {
        listUsers().then((response) => {
            setUsers(response.data);
        }).catch(error => {
            console.error(error);
        })
    }, []);


    return (
        <div className="container">
            <h2 className="text-center">List of users</h2>
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
