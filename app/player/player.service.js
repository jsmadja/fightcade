const PlayerDb = require('./player.db');
const moment = require('moment');
const _ = require('lodash');

const PlayerService = {};

const toCSV = count => {
    let sb = 'hours,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday\n';
    for (let hour = 0; hour < 24; hour++) {
        sb += hour;
        for (let day = 0; day < 7; day++) {
            sb += `,${count[day][hour]}`;
        }
        sb += '\n';
    }
    return sb.trim();
};

const createAttendanceMatrix = (offset, rows, forcedOffset) => {
    const timezoneOffset = forcedOffset || new Date().getTimezoneOffset();
    const offsetInHours = (timezoneOffset - offset) / 60;
    const count = new Array(7);
    for (let i = 0; i < count.length; i++) {
        count[i] = _.fill(new Array(24), 0);
    }
    for (let result of rows) {
        const date = moment(result.date).add(offsetInHours, 'hours');
        const day = (date.day() + 6) % 7;
        const hour = date.hour();
        const players = result.count;
        if (count[day][hour] == 0) {
            count[day][hour] = players;
        }
    }
    return count;
};

PlayerService.getAttendance = (game, country, offset, forcedOffset) => {
    if (!offset) {
        offset = new Date().getTimezoneOffset();
    }
    if ('Anywhere' === country) {
        country = null;
    }
    return PlayerDb
        .getAttendance(game, country)
        .then(rows => createAttendanceMatrix(offset, rows, forcedOffset))
        .then(toCSV);
};

module.exports = PlayerService;