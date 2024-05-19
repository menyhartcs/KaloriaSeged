import React from "react";

const UserComponent = () => {
    return (
        <div className="container">
            <br/><br/>
            <div className="row">
                <div className="card col-md-6 offset-md-3 offset-md-3">
                    <h2 className="text-center">Add User</h2>
                    <div className="card-body">
                        <form>
                            <div className="form-group mb-2">
                                <label className="form-label">Name:</label>
                                <input type="text"
                                       placeholder="Enter username"
                                       name="name" value={name}
                                       className="form-control"
                                       onChange={(e) => setName(e.target.value)}
                                />
                            </div>

                            <div className="form-group mb-2">
                                <label className="form-label">Email:</label>
                                <input type="text"
                                       placeholder="Enter email address"
                                       name="email" value={email}
                                       className="form-control"
                                       onChange={(e) => setEmail(e.target.value)}
                                />
                            </div>

                            <div className="form-group mb-2">
                                <label className="form-label">Mobile:</label>
                                <input type="text"
                                       placeholder="Enter mobile number"
                                       name="mobile" value={mobile}
                                       className="form-control"
                                       onChange={(e) => setMobile(e.target.value)}
                                />
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