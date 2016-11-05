'use strict';

const assert = require('assert');
const API = require('./api');

describe('Player', function () {
    before(() => {
        require('../../app/index');
    });

    it('get attendance for 1941 @ Bahrain', (done) => {
        API.getAttendance('1941', 'Bahrain', '-60')
            .then(res => {
                assert.equal(res.body,
                `hours,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday
0,0,0,0,0,0,0,0
1,0,0,0,0,0,0,0
2,0,0,0,0,0,0,0
3,0,0,0,0,0,0,0
4,0,0,0,0,0,0,0
5,0,0,0,0,0,0,0
6,0,0,0,0,0,0,0
7,0,0,0,0,0,0,0
8,0,0,0,0,0,0,0
9,0,0,0,0,0,0,0
10,0,0,0,0,0,0,0
11,0,0,0,0,0,0,0
12,0,0,0,0,0,0,0
13,0,0,0,0,0,0,0
14,0,0,0,0,0,0,0
15,0,0,0,0,0,0,0
16,0,0,0,0,0,0,0
17,0,0,0,0,0,0,0
18,0,0,0,0,0,0,0
19,0,0,0,0,0,0,0
20,0,0,0,0,0,0,0
21,0,0,0,0,0,0,0
22,0,0,0,0,0,0,0
23,0,0,0,0,0,0,0`);
            })
            .then(done)
            .catch(done);
    });

    it('get attendance for garou @ Japan', (done) => {
        API.getAttendance('garou', 'Japan', '-60')
            .then(res => {
                assert.equal(res.body,
                    `hours,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday
0,2,0,0,0,1,1,3
1,0,1,2,2,0,2,1
2,0,0,1,1,1,0,1
3,1,0,3,1,2,0,2
4,0,0,2,3,1,1,2
5,1,1,2,1,1,0,1
6,0,0,2,1,1,2,1
7,1,1,2,2,2,1,3
8,1,1,1,3,2,2,3
9,0,1,2,2,2,3,2
10,0,4,1,0,3,5,3
11,0,2,2,2,1,3,2
12,1,1,4,0,2,4,4
13,5,5,3,1,2,3,2
14,2,3,5,3,5,1,3
15,4,4,6,2,2,2,1
16,3,4,3,2,4,4,3
17,7,5,1,0,3,5,2
18,1,4,1,1,1,3,0
19,2,2,0,4,2,0,2
20,5,3,1,1,2,1,1
21,0,1,0,0,1,0,1
22,0,0,0,1,0,1,0
23,0,0,2,2,2,0,1`);
            })
            .then(done)
            .catch(done);
    });
});