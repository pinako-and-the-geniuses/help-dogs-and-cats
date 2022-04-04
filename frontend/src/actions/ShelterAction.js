// Action types
const GETSHELTERLDATA = "GETSHELTERLDATA";

// Action creators
function shelterGetAction(info) {
  return {
    type: GETSHELTERLDATA,
    info: info,
  };
}

export { shelterGetAction, GETSHELTERLDATA };
