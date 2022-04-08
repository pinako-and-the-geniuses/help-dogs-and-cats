// Action types
const GETANIMALDATA = "GETANIMALDATA";

// Action creators
function animalGetAction(info) {
  return {
    type: GETANIMALDATA,
    info: info,
  };
}

export { animalGetAction, GETANIMALDATA };
