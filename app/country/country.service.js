const CountryDb = require('./country.db');

const CountryService = {};

CountryService.find = type => CountryDb.list().map((country) => country.name);

module.exports = CountryService;