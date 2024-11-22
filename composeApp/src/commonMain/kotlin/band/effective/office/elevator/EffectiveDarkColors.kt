package band.effective.office.elevator

val EffectiveDarkColors = EffectiveThemeColors(
    background = EffectiveThemeColors.Background(
        primary = neutral95,
        secondary = neutral90,
        tertiary = neutral95.copy(alpha = 0.8f),
        additional = neutral80,
    ),

    text = EffectiveThemeColors.Text(
        primary = neutral10,
        secondary = neutral50,
        tertiary = orange30,
        accent = orange50,
        primaryEvent = neutral10,
        error = red40,
        additional = neutral40,
        caption = neutral20,
        optional = purple30,
    ),

    icon = EffectiveThemeColors.Icon(
        primary = neutral10,
        secondary = neutral25,
        tertiary = neutral35,
        accent = orange50,
        success = green40,
        error = red40,
        primaryEvent = neutral10,
        optional = purple30,
    ),

    stroke = EffectiveThemeColors.Stroke(
        primary = neutral85,
        error = red40,
        accent = orange50,
    ),

    divider = EffectiveThemeColors.Divider(
        primary = neutral85,
    ),

    graph = EffectiveThemeColors.Graph(
        orange = orange40,
        violet = purple50,
    ),

    button = EffectiveThemeColors.Button(
        primaryNormal = orange50,
        primaryPress = orange40,
        primaryDisable = orange90,
        iconNormal = neutral95,
        iconPress = neutral90,
        iconDisable = neutral95,
        tertiaryNormal = neutral95,
        tertiaryPress = neutral90,
        actionNormalNormal = purple30,
        actionEditPress = purple20,
        actionDelete = red20,
        timeNormal = neutral90,
        timePress = neutral95,
        timeActive = neutral90,
        fullDayNormal = neutral90,
        fullDayPress = neutral95,
        repeatNormal = neutral90,
        repeatPress = neutral95,
        toggleOn = green40,
        toggleOff = neutral80,
        toggleNormal = neutral5,
    ),

    calendar = EffectiveThemeColors.Calendar(
        date = purple30,
    ),

    item = EffectiveThemeColors.Item(
        colleagueNormal = neutral95,
        colleagueRepeat = neutral90,
        repeatNormal = neutral90,
        repeatPress = neutral85,
    ),

    avatar = EffectiveThemeColors.Avatar(
        default = neutral90,
    ),

    card = EffectiveThemeColors.Card(
        normal = neutral95,
        active = neutral95,
        press = neutral90,
    ),

    input = EffectiveThemeColors.Input(
        freeNormal = neutral95,
        freePress = neutral90,
        freeTyping = neutral95,
        freeFill = neutral95,
        lockNormal = neutral90,
        lockPress = neutral95,
        lockTyping = neutral90,
        lockFill = neutral90,
        lockDisable = neutral95,
        searchNormal = neutral95,
        searchPress = neutral90,
        searchTyping = neutral95,
        searchFill = neutral95,
    ),

    table = EffectiveThemeColors.Table(
        tableAvailable = neutral85,
        tableSelect = purple40,
    )
)
