import style from './manager.module.scss';

function ManageHome(){

    return(
        <div className={style.home}>
            <div className={`${style.volCondition} ${style.condition}`}>
                <p>봉사 현황</p>
                <div className={style.box}>
                    dd
                </div>
            </div>

            <div className={`${style.adoptCondition} ${style.condition}`}>
                <p>입양 현황</p>
                <div className={style.box}>
                    ddd
                </div>
            </div>
        </div>
    )
}

export default ManageHome;