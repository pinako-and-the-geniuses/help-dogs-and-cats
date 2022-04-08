import axios from "axios";
import { useEffect } from "react";
import { URL } from "public/config";

export default function AdoptDetail({ seq }) {
  const jwt = sessionStorage.getItem("jwt");

  useEffect(() => {
    axios.get(`${URL}/adopts/auth/${seq}`, {
      headers: { Authorization: `Bearer ${jwt}` },
    });
  }, []);

  return (
    <div>
      <div class="modal" id="myModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">공지</h5>
              <button
                type="button"
                class="close"
                data-dismiss="modal"
                aria-label="Close"
              >
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
              <p>블라블라</p>
            </div>
            <div class="modal-footer">
              <button
                type="button"
                class="btn btn-primary"
                id="modal-today-close"
              >
                today
              </button>
              <button
                type="button"
                class="btn btn-secondary"
                data-dismiss="modal"
              >
                Close
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
