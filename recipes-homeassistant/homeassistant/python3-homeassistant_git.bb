SUMMARY = "Open-source home automation platform running on Python 3"
HOMEPAGE = "https://home-assistant.io/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=f4eda51018051de136d3b3742e9a7a40"

HOMEASSISTANT_CONFIG_DIR ?= "${localstatedir}/lib/homeassistant"
HOMEASSISTANT_CONFIG_DIR[doc] = "Configuration directory used by home-assistant."

inherit setuptools3 useradd systemd

SRCREV = "b59b42db2c72c28d5ce76a8ba7bac601e7de00ee"
PV = "0.38.1"
SRC_URI += "git://github.com/home-assistant/home-assistant.git;protocol=https"
S = "${WORKDIR}/git"

SRC_URI += "\
    file://homeassistant.service \
    file://configuration.yaml \
    file://0001-remove-typing-it-is-already-included-in-python-3.5.patch \
    "

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM_${PN} = "homeassistant"
USERADD_PARAM_${PN} = "--system --home ${HOMEASSISTANT_CONFIG_DIR} \
                       --no-create-home --shell /bin/false \
                       --groups homeassistant,dialout --gid homeassistant homeassistant"

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "homeassistant.service"

do_install_append () {
    install -d -o homeassistant -g homeassistant ${D}${HOMEASSISTANT_CONFIG_DIR}

    # Install systemd unit files and set correct config directory
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/homeassistant.service ${D}${systemd_unitdir}/system
    sed -i -e 's,@HOMEASSISTANT_CONFIG_DIR@,${HOMEASSISTANT_CONFIG_DIR},g' ${D}${systemd_unitdir}/system/homeassistant.service
}

# Home Assistant core
RDEPENDS_${PN} = " \
    python3-requests (>=2) \
    python3-pyyaml (>=3.11)  \
    python3-pytz (>=2016.10) \
    python3-jinja2 (>=2.8) \
    python3-voluptuous (=0.9.3) \
    python3-typing (>=3) \
    python3-aiohttp (=1.3.1)\
    python3-async-timeout (=1.1.0) \
    \
    python3-asyncio \
    python3-multiprocessing \
    python3-sqlite3 \
    python3-html \
    \
    python3-pip \
    "


# homeassistant.components.http
RDEPENDS_${PN} += " \
    python3-aiohttp-cors (=0.5.0) \
    "

# homeassistant.components.recorder
# homeassistant.scripts.db_migrator
RDEPENDS_${PN} += " \
    python3-sqlalchemy (=1.1.5) \
    "

# homeassistant.components.discovery
RDEPENDS_${PN} += " \
    python3-netdisco (=0.8.2) \
    "

# homeassistant.components.sun
RDEPENDS_${PN} += " \
    python3-astral (=1.3.4) \
    "

# homeassistant.components.sensor.swiss_hydrological_data
# homeassistant.components.sensor.ted5000
# homeassistant.components.sensor.yr
RDEPENDS_${PN} += " \
    python3-xmltodict (=0.10.2)\
    "

# homeassistant.components.updater
RDEPENDS_${PN} += " \
    python3-distro (=1.0.2) \
    "

# homeassistant.components.conversation
RDEPENDS_${PN} += " \
    python3-fuzzywuzzy (=0.14.0) \
    "

# homeassistant.components.tts.google
RDEPENDS_${PN} += " \
    python3-gtts-token (=1.1.1) \
    "

# homeassistant.components.google
RDEPENDS_${PN} += " \
    python3-oauth2client (=4.0.0)\
    "

# homeassistant.components.google
RDEPENDS_${PN} += " \
    python3-google-api-python-client (=1.6.2) \
    "

# homeassistant.components.light.hue
RDEPENDS_${PN} += " \
    python3-phue (=0.9) \
    "

# homeassistant.components.hdmi_cec
RDEPENDS_${PN} += " \
    python3-pycec (=0.4.13) \
    "

# homeassistant.components.knx
RDEPENDS_${PN} += " \
    python3-knxip (=0.3.3) \
    "
