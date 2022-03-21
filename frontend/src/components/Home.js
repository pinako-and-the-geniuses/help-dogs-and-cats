// import style from './styles/Home.module.scss';
import './styles/Home.css';
// import './main.js';

function Home(){
  
    return(
        // <>
        //     <div className={style.home_img}>
        //         <h1>안녕하세요</h1>
        //     </div>
        //     <div className={style.div1}>
        //     </div>
        //     <div className={style.div2}></div>
        // </>
        <>
            <div id="hero" class="home">
                <div class="container">
                    <div class="hero-content">
                        <h1>I'm <span class="typed">안녕</span></h1>
                        <p>Designer, Developer, Freelancer, Photographer</p>
                    </div>
                </div>       
            </div>

            <main>
                <div className="div1"></div>
                <div className="div2"></div>
            </main>
        </>
    )
}

export default Home;