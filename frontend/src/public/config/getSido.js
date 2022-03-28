import axios from 'axios';
import { ANIMAL } from './index';

export async function getSido(){
    const APP_KEY = process.env.REACT_APP_ANIMAL_API;
    // const [cd, setCd] = useState();

    await axios({
        url: `${ANIMAL}/abandonmentPublicSrvc/sido?numOfRows=20&pageNo=1&_type=json&serviceKey=${APP_KEY}`,
        method: "get",
    })
    // .then((res) => console.log(res.data.response.body.items.item[0].orgdownNm))
    // .catch((err) => console.log(err));

    //value = orgCd
    //표기 = orgdownNm
};