export function apiTestAnnualBreed() {
  return fetch(`http://j6a302.p.ssafy.io:8090/hadoop/v1/annual-breed`).then(
    (response) => response.json()
  );
}

export function apiTestAnnualState() {
  return fetch(`http://j6a302.p.ssafy.io:8090/hadoop/v1/annual-state`).then(
    (response) => response.json()
  );
}

export function apiTestAgeState() {
  return fetch(`http://j6a302.p.ssafy.io:8090/hadoop/v1/age-state`).then(
    (response) => response.json()
  );
}

export function apiTestSpeciesNeutral() {
  return fetch(`http://j6a302.p.ssafy.io:8090/hadoop/v1/species-neutral`).then(
    (response) => response.json()
  );
}
