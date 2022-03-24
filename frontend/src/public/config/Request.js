import axios from "axios";
import { URL } from "./index";

// axios.defaults.withCredentials = true; // 쿠키 데이터를 전송받기 위해

export default function Request(method, url, data) {
  // console.log(method, URL + url, data);
  return axios({
    method,
    url: URL + url,
    data,
  })
    .then((res) => console.log(res))
    .catch((err) => console.log(err.status));
}
