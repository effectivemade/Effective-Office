package band.effective.office.network.api.impl

//class ApiMock(private val realApi: Api, mockFactory: MockFactory) : Api {
//    var getRealResponse: Boolean = true
//    private val workspaces: List<WorkspaceDTO> = mockFactory.workspaces()
//    private val meetingRooms: List<WorkspaceDTO> = mockFactory.meetingRooms()
//    private val users: MutableStateFlow<List<UserDTO>> = MutableStateFlow(mockFactory.users())
//    private val bookings: MutableStateFlow<List<BookingDTO>> = MutableStateFlow(
//        mockFactory.bookings(
//            workspaceDTO = (workspaces + meetingRooms).first { it.name == "Sirius" },
//            owner = users.value
//        )
//    )
//    private val successResponse = mockFactory.success()
//
//
//    private fun <T> response(mock: T?, realResponse: Either<ErrorResponse, T>) =
//        with(getRealResponse) {
//            when {
//                this && !(realResponse.requestNotExist()) -> realResponse
//                mock == null -> Either.Error(ErrorResponse.getResponse(404))
//                else -> Either.Success(mock)
//            }
//        }
//
//    private fun <T> Either<ErrorResponse, T>.requestNotExist() =
//        this is Either.Error
//
//    override suspend fun getWorkspace(id: String): Either<ErrorResponse, WorkspaceDTO> = response(
//        mock = (workspaces + meetingRooms).firstOrNull() { it.id == id },
//        realResponse = realApi.getWorkspace(id)
//    )
//
//    override suspend fun getWorkspaces(
//        tag: String,
//        freeFrom: Long?,
//        freeUntil: Long?
//    ): Either<ErrorResponse, List<WorkspaceDTO>> =
//        response(
//            mock = if (tag == "meeting") meetingRooms else workspaces,
//            realResponse = realApi.getWorkspaces(
//                tag = tag,
//                freeFrom = freeFrom,
//                freeUntil = freeUntil
//            )
//        )
//
//    override suspend fun getZones(): Either<ErrorResponse, List<WorkspaceZoneDTO>> = response(
//        mock = listOf(),
//        realResponse = realApi.getZones()
//    )
//
//    override suspend fun getUser(id: String): Either<ErrorResponse, UserDTO> = response(
//        mock = users.value.firstOrNull { it.id == id },
//        realResponse = realApi.getUser(id)
//    )
//
//    override suspend fun getUsers(tag: String): Either<ErrorResponse, List<UserDTO>> = response(
//        mock = users.value,
//        realResponse = realApi.getUsers(tag = tag)
//    )
//
//    override suspend fun updateUser(user: UserDTO): Either<ErrorResponse, UserDTO> = response(
//        mock = users.update { it.map { saveUser -> if (saveUser.id == user.id) user else saveUser } }
//            .run { user },
//        realResponse = realApi.updateUser(user = user)
//    )
//
//    override suspend fun getBooking(id: String): Either<ErrorResponse, BookingDTO> = response(
//        mock = bookings.value.firstOrNull { it.id == id },
//        realResponse = realApi.getBooking(id = id)
//    )
//
//    override suspend fun getBookingsByUser(userId: String, beginDate: Long, endDate: Long): Either<ErrorResponse, List<BookingDTO>> =
//        response(
//            mock = bookings.value.filter { it.owner.id == userId },
//            realResponse = realApi
//                .getBookingsByUser(
//                    userId = userId,
//                    beginDate = beginDate,
//                    endDate = endDate
//                )
//        )
//
//    override suspend fun getBookingsByWorkspaces(workspaceId: String): Either<ErrorResponse, List<BookingDTO>> =
//        response(
//            mock = bookings.value.filter { it.workspace.id == workspaceId },
//            realResponse = realApi.getBookingsByWorkspaces(workspaceId = workspaceId)
//        )
//
//    override suspend fun createBooking(bookingInfo: BookingDTO): Either<ErrorResponse, BookingDTO> =
//        response(
//            mock = bookingInfo.apply {
//                bookings.update {
//                    it + bookingInfo.copy(
//                        id = "${Random.nextInt(1000)}"
//                    )
//                }
//            },
//            realResponse = realApi.createBooking(bookingInfo)
//        )
//
//    override suspend fun updateBooking(
//        bookingInfo: BookingDTO
//    ): Either<ErrorResponse, BookingDTO> = response(
//        mock = bookingInfo.apply {
//            bookings.update {
//                val a = it
//                a.map { element ->
//                    if (element.id == bookingInfo.id) bookingInfo else element
//                }
//            }
//        },
//        realResponse = realApi.updateBooking(bookingInfo)
//    )
//
//    override suspend fun deleteBooking(
//        bookingId: String
//    ): Either<ErrorResponse, SuccessResponse> = response(
//        mock = successResponse.apply { bookings.update { it.filter { element -> element.id != bookingId } } },
//        realResponse = realApi.deleteBooking(bookingId)
//    )
//
//    override fun subscribeOnWorkspaceUpdates(
//        id: String,
//        scope: CoroutineScope
//    ): Flow<Either<ErrorResponse, WorkspaceDTO>> =
//        flow {
//            realApi.subscribeOnWorkspaceUpdates(id, scope).collect { if (getRealResponse) emit(it) }
//        }
//
//    override fun subscribeOnOrganizersList(scope: CoroutineScope): Flow<Either<ErrorResponse, List<UserDTO>>> =
//        channelFlow {
//            launch { users.collect { if (!getRealResponse) send(Either.Success(it)) } }
//            launch {
//                realApi.subscribeOnOrganizersList(scope).collect { if (getRealResponse) send(it) }
//            }
//            awaitClose()
//        }
//
//    override fun subscribeOnBookingsList(
//        workspaceId: String,
//        scope: CoroutineScope
//    ): Flow<Either<ErrorResponse, List<BookingDTO>>> =
//        channelFlow {
//            launch {
//                bookings.collect {
//                    if (!getRealResponse) send(Either.Success(it.filter { item -> item.workspace.id == workspaceId }))
//                }
//            }
//            launch {
//                realApi.subscribeOnBookingsList(workspaceId, scope)
//                    .collect { if (getRealResponse) send(it) }
//            }
//            awaitClose()
//        }
//
//
//    override suspend fun getUserByEmail(email: String): Either<ErrorResponse, UserDTO> =
//        response(
//            mock = users.value.find { user ->
//                user.integrations?.find { integration ->
//                    integration.name == "email"
//                }?.value == email
//            },
//            realResponse = realApi.getUserByEmail(email)
//        )
//
//    override suspend fun getBookings(
//        freeFrom: Long?,
//        rangeTo: Long?
//    ): Either<ErrorResponse, List<BookingDTO>> {
//        TODO("Not yet implemented")
//    }
//
//}
