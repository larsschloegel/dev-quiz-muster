import {useState} from "react";

const initialState = {
    username: "",
    password: "",
}
export default function LoginPage({login}) {
    const [credentials, setCredentials] = useState(initialState);

    const handleChange = event => {
        setCredentials({ ...credentials, [event.target.name]: event.target.value })
    }

    const handleSubmit = event => {
        event.preventDefault()
        login(credentials.username, credentials.password)
    }

    return (
        <div>
            <h2>Login</h2>
            <form onSubmit={handleSubmit}>
                <label>
                    <input
                        type="text"
                        placeholder="username"
                        required
                        name="username"
                        onChange={handleChange}
                        value={credentials.username}
                    />
                </label>
                <label>
                    <input
                        type="password"
                        required
                        placeholder="password"
                        name="password"
                        onChange={handleChange}
                        value={credentials.password}
                    />
                </label>
                <button>Login</button>
            </form>
        </div>
    )
}