import { useEffect, useState } from "react";
import { FcLock } from "react-icons/fc";
import st from "../styles/profile.module.scss";
const IMGURL = "http://j6a302.p.ssafy.io:8080";

export default function ProfileBadge(badgesForProfile) {
  const [badges, setBadges] = useState([]);
  useEffect(() => {
    if (badgesForProfile) {
      setBadges(badgesForProfile.badgesForProfile);
    } else {
      alert("다시 접속해 주세요.");
      console.log("실패");
    }
  }, [badgesForProfile]);

  if (badges.length === 10) {
    return (
      <div className="mt-5">
        <div className="d-flex">
          {badges[0].achieve == true ? (
            <div className={st.badgeBox}>
              <img
                className={st.badge}
                src={`${IMGURL}${badges[0].imageFilePath}`}
                data-bs-toggle="modal"
                data-bs-target="#badgeModal"
              />
              <div
                className="modal fade"
                id="badgeModal"
                tabIndex="-1"
                aria-labelledby="ModalLabel"
                aria-hidden="true"
              >
                <div className="modal-dialog modal-dialog-centered">
                  <div className="modal-content">
                    <div className={st.modalHeader}>
                      <div className={st.modalBadgeBox}>
                        <img
                          className={st.badge}
                          src={`${IMGURL}${badges[0].imageFilePath}`}
                          data-bs-toggle="modal"
                          data-bs-target="#badgeModal"
                        />
                      </div>
                      <h5 className="modal-title" id="ModalLabel">
                        {badges[0].name}
                      </h5>
                      <button
                        type="button"
                        className="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      ></button>
                    </div>
                    <div className={st.modalBody}>{badges[0].howToGet}</div>
                    {badges[0].achieve == true && (
                      <div className={st.modalBody}>{badges[0].content}</div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          ) : (
            <div className={st.badgeBox}>
              <FcLock
                size={50}
                data-bs-toggle="modal"
                data-bs-target="#badgeModal"
              />
              <div
                className="modal fade"
                id="badgeModal"
                tabIndex="-1"
                aria-labelledby="ModalLabel"
                aria-hidden="true"
              >
                <div className="modal-dialog modal-dialog-centered">
                  <div className="modal-content">
                    <div className={st.modalHeader}>
                      <div className={st.modalBadgeBox}>
                        <FcLock size={50} />
                      </div>
                      <h5 className="modal-title" id="ModalLabel">
                        {badges[0].name}
                      </h5>
                      <button
                        type="button"
                        className="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      ></button>
                    </div>
                    <div className={st.modalBody}>{badges[0].howToGet}</div>
                    {badges[0].achieve == true && (
                      <div className={st.modalBody}>{badges[0].content}</div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          )}
          {badges[1].achieve == true ? (
            <div className={st.badgeBox}>
              <img
                className={st.badge}
                src={`${IMGURL}${badges[1].imageFilePath}`}
                data-bs-toggle="modal"
                data-bs-target="#badgeModal1"
              />
              <div
                className="modal fade"
                id="badgeModal1"
                tabIndex="-1"
                aria-labelledby="ModalLabel"
                aria-hidden="true"
              >
                <div className="modal-dialog modal-dialog-centered">
                  <div className="modal-content">
                    <div className={st.modalHeader}>
                      <div className={st.modalBadgeBox}>
                        <img
                          className={st.badge}
                          src={`${IMGURL}${badges[1].imageFilePath}`}
                        />
                      </div>
                      <h5 className="modal-title" id="ModalLabel">
                        {badges[1].name}
                      </h5>
                      <button
                        type="button"
                        className="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      ></button>
                    </div>
                    <div className={st.modalBody}>{badges[1].howToGet}</div>
                    {badges[1].achieve == true && (
                      <div className={st.modalBody}>{badges[1].content}</div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          ) : (
            <div className={st.badgeBox}>
              <FcLock
                size={50}
                data-bs-toggle="modal"
                data-bs-target="#badgeModal1"
              />
              <div
                className="modal fade"
                id="badgeModal1"
                tabIndex="-1"
                aria-labelledby="ModalLabel"
                aria-hidden="true"
              >
                <div className="modal-dialog modal-dialog-centered">
                  <div className="modal-content">
                    <div className={st.modalHeader}>
                      <div className={st.modalBadgeBox}>
                        <FcLock size={50} />
                      </div>
                      <h5 className="modal-title" id="ModalLabel">
                        {badges[1].name}
                      </h5>
                      <button
                        type="button"
                        className="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      ></button>
                    </div>
                    <div className={st.modalBody}>{badges[1].howToGet}</div>
                    {badges[1].achieve == true && (
                      <div className={st.modalBody}>{badges[1].content}</div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          )}
          {badges[2].achieve == true ? (
            <div className={st.badgeBox}>
              <img
                className={st.badge}
                src={`${IMGURL}${badges[2].imageFilePath}`}
                data-bs-toggle="modal"
                data-bs-target="#badgeModal2"
              />
              <div
                className="modal fade"
                id="badgeModal2"
                tabIndex="-1"
                aria-labelledby="ModalLabel"
                aria-hidden="true"
              >
                <div className="modal-dialog modal-dialog-centered">
                  <div className="modal-content">
                    <div className={st.modalHeader}>
                      <div className={st.modalBadgeBox}>
                        <img
                          className={st.badge}
                          src={`${IMGURL}${badges[2].imageFilePath}`}
                        />
                      </div>
                      <h5 className="modal-title" id="ModalLabel">
                        {badges[2].name}
                      </h5>
                      <button
                        type="button"
                        className="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      ></button>
                    </div>
                    <div className={st.modalBody}>{badges[2].howToGet}</div>
                    {badges[2].achieve == true && (
                      <div className={st.modalBody}>{badges[2].content}</div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          ) : (
            <div className={st.badgeBox}>
              <FcLock
                size={50}
                data-bs-toggle="modal"
                data-bs-target="#badgeModal2"
              />
              <div
                className="modal fade"
                id="badgeModal2"
                tabIndex="-1"
                aria-labelledby="ModalLabel"
                aria-hidden="true"
              >
                <div className="modal-dialog modal-dialog-centered">
                  <div className="modal-content">
                    <div className={st.modalHeader}>
                      <div className={st.modalBadgeBox}>
                        <FcLock size={50} />
                      </div>
                      <h5 className="modal-title" id="ModalLabel">
                        {badges[2].name}
                      </h5>
                      <button
                        type="button"
                        className="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      ></button>
                    </div>
                    <div className={st.modalBody}>{badges[2].howToGet}</div>
                    {badges[2].achieve == true && (
                      <div className={st.modalBody}>{badges[2].content}</div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          )}
          {badges[3].achieve == true ? (
            <div className={st.badgeBox}>
              <img
                className={st.badge}
                src={`${IMGURL}${badges[3].imageFilePath}`}
                data-bs-toggle="modal"
                data-bs-target="#badgeModal3"
              />
              <div
                className="modal fade"
                id="badgeModal3"
                tabIndex="-1"
                aria-labelledby="ModalLabel"
                aria-hidden="true"
              >
                <div className="modal-dialog modal-dialog-centered">
                  <div className="modal-content">
                    <div className={st.modalHeader}>
                      <div className={st.modalBadgeBox}>
                        <img
                          className={st.badge}
                          src={`${IMGURL}${badges[3].imageFilePath}`}
                        />
                      </div>
                      <h5 className="modal-title" id="ModalLabel">
                        {badges[3].name}
                      </h5>
                      <button
                        type="button"
                        className="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      ></button>
                    </div>
                    <div className={st.modalBody}>{badges[3].howToGet}</div>
                    {badges[3].achieve == true && (
                      <div className={st.modalBody}>{badges[3].content}</div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          ) : (
            <div className={st.badgeBox}>
              <FcLock
                size={50}
                data-bs-toggle="modal"
                data-bs-target="#badgeModal3"
              />
              <div
                className="modal fade"
                id="badgeModal3"
                tabIndex="-1"
                aria-labelledby="ModalLabel"
                aria-hidden="true"
              >
                <div className="modal-dialog modal-dialog-centered">
                  <div className="modal-content">
                    <div className={st.modalHeader}>
                      <div className={st.modalBadgeBox}>
                        <FcLock size={50} />
                      </div>
                      <h5 className="modal-title" id="ModalLabel">
                        {badges[3].name}
                      </h5>
                      <button
                        type="button"
                        className="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      ></button>
                    </div>
                    <div className={st.modalBody}>{badges[3].howToGet}</div>
                    {badges[3].achieve == true && (
                      <div className={st.modalBody}>{badges[3].content}</div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          )}
          {badges[4].achieve == true ? (
            <div className={st.badgeBox}>
              <img
                className={st.badge}
                src={`${IMGURL}${badges[4].imageFilePath}`}
                data-bs-toggle="modal"
                data-bs-target="#badgeModal4"
              />
              <div
                className="modal fade"
                id="badgeModal4"
                tabIndex="-1"
                aria-labelledby="ModalLabel"
                aria-hidden="true"
              >
                <div className="modal-dialog modal-dialog-centered">
                  <div className="modal-content">
                    <div className={st.modalHeader}>
                      <div className={st.modalBadgeBox}>
                        <img
                          className={st.badge}
                          src={`${IMGURL}${badges[4].imageFilePath}`}
                        />
                      </div>
                      <h5 className="modal-title" id="ModalLabel">
                        {badges[4].name}
                      </h5>
                      <button
                        type="button"
                        className="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      ></button>
                    </div>
                    <div className={st.modalBody}>{badges[4].howToGet}</div>
                    {badges[4].achieve == true && (
                      <div className={st.modalBody}>{badges[4].content}</div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          ) : (
            <div className={st.badgeBox}>
              <FcLock
                size={50}
                data-bs-toggle="modal"
                data-bs-target="#badgeModal4"
              />
              <div
                className="modal fade"
                id="badgeModal4"
                tabIndex="-1"
                aria-labelledby="ModalLabel"
                aria-hidden="true"
              >
                <div className="modal-dialog modal-dialog-centered">
                  <div className="modal-content">
                    <div className={st.modalHeader}>
                      <div className={st.modalBadgeBox}>
                        <FcLock size={50} />
                      </div>
                      <h5 className="modal-title" id="ModalLabel">
                        {badges[4].name}
                      </h5>
                      <button
                        type="button"
                        className="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      ></button>
                    </div>
                    <div className={st.modalBody}>{badges[4].howToGet}</div>
                    {badges[4].achieve == true && (
                      <div className={st.modalBody}>{badges[4].content}</div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>

        <div className="d-flex mt-4">
          {badges[5].achieve == true ? (
            <div className={st.badgeBox}>
              <img
                className={st.badge}
                src={`${IMGURL}${badges[5].imageFilePath}`}
                data-bs-toggle="modal"
                data-bs-target="#badgeModal5"
              />
              <div
                className="modal fade"
                id="badgeModal5"
                tabIndex="-1"
                aria-labelledby="ModalLabel"
                aria-hidden="true"
              >
                <div className="modal-dialog modal-dialog-centered">
                  <div className="modal-content">
                    <div className={st.modalHeader}>
                      <div className={st.modalBadgeBox}>
                        <img
                          className={st.badge}
                          src={`${IMGURL}${badges[5].imageFilePath}`}
                        />
                      </div>
                      <h5 className="modal-title" id="ModalLabel">
                        {badges[5].name}
                      </h5>
                      <button
                        type="button"
                        className="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      ></button>
                    </div>
                    <div className={st.modalBody}>{badges[5].howToGet}</div>
                    {badges[5].achieve == true && (
                      <div className={st.modalBody}>{badges[5].content}</div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          ) : (
            <div className={st.badgeBox}>
              <FcLock
                size={50}
                data-bs-toggle="modal"
                data-bs-target="#badgeModal5"
              />{" "}
              <div
                className="modal fade"
                id="badgeModal5"
                tabIndex="-1"
                aria-labelledby="ModalLabel"
                aria-hidden="true"
              >
                <div className="modal-dialog modal-dialog-centered">
                  <div className="modal-content">
                    <div className={st.modalHeader}>
                      <div className={st.modalBadgeBox}>
                        <FcLock size={50} />
                      </div>
                      <h5 className="modal-title" id="ModalLabel">
                        {badges[5].name}
                      </h5>
                      <button
                        type="button"
                        className="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      ></button>
                    </div>
                    <div className={st.modalBody}>{badges[5].howToGet}</div>
                    {badges[5].achieve == true && (
                      <div className={st.modalBody}>{badges[5].content}</div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          )}
          {badges[6].achieve == true ? (
            <div className={st.badgeBox}>
              <img
                className={st.badge}
                src={`${IMGURL}${badges[6].imageFilePath}`}
                data-bs-toggle="modal"
                data-bs-target="#badgeModal6"
              />
              <div
                className="modal fade"
                id="badgeModal6"
                tabIndex="-1"
                aria-labelledby="ModalLabel"
                aria-hidden="true"
              >
                <div className="modal-dialog modal-dialog-centered">
                  <div className="modal-content">
                    <div className={st.modalHeader}>
                      <div className={st.modalBadgeBox}>
                        <img
                          className={st.badge}
                          src={`${IMGURL}${badges[6].imageFilePath}`}
                        />
                      </div>
                      <h5 className="modal-title" id="ModalLabel">
                        {badges[6].name}
                      </h5>
                      <button
                        type="button"
                        className="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      ></button>
                    </div>
                    <div className={st.modalBody}>{badges[6].howToGet}</div>
                    {badges[6].achieve == true && (
                      <div className={st.modalBody}>{badges[6].content}</div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          ) : (
            <div className={st.badgeBox}>
              <FcLock
                size={50}
                data-bs-toggle="modal"
                data-bs-target="#badgeModal6"
              />
              <div
                className="modal fade"
                id="badgeModal6"
                tabIndex="-1"
                aria-labelledby="ModalLabel"
                aria-hidden="true"
              >
                <div className="modal-dialog modal-dialog-centered">
                  <div className="modal-content">
                    <div className={st.modalHeader}>
                      <div className={st.modalBadgeBox}>
                        <FcLock size={50} />
                      </div>
                      <h5 className="modal-title" id="ModalLabel">
                        {badges[6].name}
                      </h5>
                      <button
                        type="button"
                        className="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      ></button>
                    </div>
                    <div className={st.modalBody}>{badges[6].howToGet}</div>
                    {badges[6].achieve == true && (
                      <div className={st.modalBody}>{badges[6].content}</div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          )}
          {badges[7].achieve == true ? (
            <div className={st.badgeBox}>
              <img
                className={st.badge}
                src={`${IMGURL}${badges[7].imageFilePath}`}
                data-bs-toggle="modal"
                data-bs-target="#badgeModal7"
              />
              <div
                className="modal fade"
                id="badgeModal7"
                tabIndex="-1"
                aria-labelledby="ModalLabel"
                aria-hidden="true"
              >
                <div className="modal-dialog modal-dialog-centered">
                  <div className="modal-content">
                    <div className={st.modalHeader}>
                      <div className={st.modalBadgeBox}>
                        <img
                          className={st.badge}
                          src={`${IMGURL}${badges[7].imageFilePath}`}
                        />
                      </div>
                      <h5 className="modal-title" id="ModalLabel">
                        {badges[7].name}
                      </h5>
                      <button
                        type="button"
                        className="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      ></button>
                    </div>
                    <div className={st.modalBody}>{badges[7].howToGet}</div>
                    {badges[7].achieve == true && (
                      <div className={st.modalBody}>{badges[7].content}</div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          ) : (
            <div className={st.badgeBox}>
              <FcLock
                size={50}
                data-bs-toggle="modal"
                data-bs-target="#badgeModal7"
              />
              <div
                className="modal fade"
                id="badgeModal7"
                tabIndex="-1"
                aria-labelledby="ModalLabel"
                aria-hidden="true"
              >
                <div className="modal-dialog modal-dialog-centered">
                  <div className="modal-content">
                    <div className={st.modalHeader}>
                      <div className={st.modalBadgeBox}>
                        <FcLock size={50} />
                      </div>
                      <h5 className="modal-title" id="ModalLabel">
                        {badges[7].name}
                      </h5>
                      <button
                        type="button"
                        className="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      ></button>
                    </div>
                    <div className={st.modalBody}>{badges[7].howToGet}</div>
                    {badges[7].achieve == true && (
                      <div className={st.modalBody}>{badges[7].content}</div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          )}
          {badges[8].achieve == true ? (
            <div className={st.badgeBox}>
              <img
                className={st.badge}
                src={`${IMGURL}${badges[8].imageFilePath}`}
                data-bs-toggle="modal"
                data-bs-target="#badgeModal8"
              />
              <div
                className="modal fade"
                id="badgeModal8"
                tabIndex="-1"
                aria-labelledby="ModalLabel"
                aria-hidden="true"
              >
                <div className="modal-dialog modal-dialog-centered">
                  <div className="modal-content">
                    <div className={st.modalHeader}>
                      <div className={st.modalBadgeBox}>
                        <img
                          className={st.badge}
                          src={`${IMGURL}${badges[8].imageFilePath}`}
                        />
                      </div>
                      <h5 className="modal-title" id="ModalLabel">
                        {badges[8].name}
                      </h5>
                      <button
                        type="button"
                        className="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      ></button>
                    </div>
                    <div className={st.modalBody}>{badges[8].howToGet}</div>
                    {badges[8].achieve == true && (
                      <div className={st.modalBody}>{badges[8].content}</div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          ) : (
            <div className={st.badgeBox}>
              <FcLock
                size={50}
                data-bs-toggle="modal"
                data-bs-target="#badgeModal8"
              />
              <div
                className="modal fade"
                id="badgeModal8"
                tabIndex="-1"
                aria-labelledby="ModalLabel"
                aria-hidden="true"
              >
                <div className="modal-dialog modal-dialog-centered">
                  <div className="modal-content">
                    <div className={st.modalHeader}>
                      <div className={st.modalBadgeBox}>
                        <FcLock size={50} />
                      </div>
                      <h5 className="modal-title" id="ModalLabel">
                        {badges[8].name}
                      </h5>
                      <button
                        type="button"
                        className="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      ></button>
                    </div>
                    <div className={st.modalBody}>{badges[8].howToGet}</div>
                    {badges[8].achieve == true && (
                      <div className={st.modalBody}>{badges[8].content}</div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          )}
          {badges[9].achieve == true ? (
            <div className={st.badgeBox}>
              <img
                className={st.badge}
                src={`${IMGURL}${badges[9].imageFilePath}`}
                data-bs-toggle="modal"
                data-bs-target="#badgeModal9"
              />
              <div
                className="modal fade"
                id="badgeModal9"
                tabIndex="-1"
                aria-labelledby="ModalLabel"
                aria-hidden="true"
              >
                <div className="modal-dialog modal-dialog-centered">
                  <div className="modal-content">
                    <div className={st.modalHeader}>
                      <div className={st.modalBadgeBox}>
                        <img
                          className={st.badge}
                          src={`${IMGURL}${badges[9].imageFilePath}`}
                        />
                      </div>
                      <h5 className="modal-title" id="ModalLabel">
                        {badges[9].name}
                      </h5>
                      <button
                        type="button"
                        className="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      ></button>
                    </div>
                    <div className={st.modalBody}>{badges[9].howToGet}</div>
                    {badges[9].achieve == true && (
                      <div className={st.modalBody}>{badges[9].content}</div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          ) : (
            <div className={st.badgeBox}>
              <FcLock
                size={50}
                data-bs-toggle="modal"
                data-bs-target="#badgeModal9"
              />
              <div
                className="modal fade"
                id="badgeModal9"
                tabIndex="-1"
                aria-labelledby="ModalLabel"
                aria-hidden="true"
              >
                <div className="modal-dialog modal-dialog-centered">
                  <div className="modal-content">
                    <div className={st.modalHeader}>
                      <div className={st.modalBadgeBox}>
                        <FcLock size={50} />
                      </div>
                      <h5 className="modal-title" id="ModalLabel">
                        {badges[9].name}
                      </h5>
                      <button
                        type="button"
                        className="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      ></button>
                    </div>
                    <div className={st.modalBody}>{badges[9].howToGet}</div>
                    {badges[9].achieve == true && (
                      <div className={st.modalBody}>{badges[9].content}</div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    );
  } else {
    return "";
  }
}
