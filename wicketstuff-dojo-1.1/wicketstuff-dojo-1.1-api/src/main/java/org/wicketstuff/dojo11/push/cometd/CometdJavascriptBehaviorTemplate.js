function onEventFor${markupId}(message){var callback = ${callback};if ((!callback || callback(message)) && '${url}') wicketDojoCometdCallback(message, '${url}')}