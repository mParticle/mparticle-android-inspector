(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin', 'kotlinx-serialization-runtime-js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'), require('kotlinx-serialization-runtime-js'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'shared'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'shared'.");
    }
    if (typeof this['kotlinx-serialization-runtime-js'] === 'undefined') {
      throw new Error("Error loading module 'shared'. Its dependency 'kotlinx-serialization-runtime-js' was not found. Please, check whether 'kotlinx-serialization-runtime-js' is loaded prior to 'shared'.");
    }
    root.shared = factory(typeof shared === 'undefined' ? {} : shared, kotlin, this['kotlinx-serialization-runtime-js']);
  }
}(this, function (_, Kotlin, $module$kotlinx_serialization_runtime_js) {
  'use strict';
  var Enum = Kotlin.kotlin.Enum;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var throwISE = Kotlin.throwISE;
  var JsonConfiguration = $module$kotlinx_serialization_runtime_js.kotlinx.serialization.json.JsonConfiguration;
  var Json = $module$kotlinx_serialization_runtime_js.kotlinx.serialization.json.Json;
  var getKClass = Kotlin.getKClass;
  var Unit = Kotlin.kotlin.Unit;
  var PrimitiveClasses$anyClass = Kotlin.kotlin.reflect.js.internal.PrimitiveClasses.anyClass;
  var PrimitiveClasses$intClass = Kotlin.kotlin.reflect.js.internal.PrimitiveClasses.intClass;
  var kotlin_js_internal_IntCompanionObject = Kotlin.kotlin.js.internal.IntCompanionObject;
  var serializer = $module$kotlinx_serialization_runtime_js.kotlinx.serialization.serializer_qn7glr$;
  var PrimitiveClasses$doubleClass = Kotlin.kotlin.reflect.js.internal.PrimitiveClasses.doubleClass;
  var kotlin_js_internal_DoubleCompanionObject = Kotlin.kotlin.js.internal.DoubleCompanionObject;
  var serializer_0 = $module$kotlinx_serialization_runtime_js.kotlinx.serialization.serializer_6a53gt$;
  var PrimitiveClasses$booleanClass = Kotlin.kotlin.reflect.js.internal.PrimitiveClasses.booleanClass;
  var kotlin_js_internal_BooleanCompanionObject = Kotlin.kotlin.js.internal.BooleanCompanionObject;
  var serializer_1 = $module$kotlinx_serialization_runtime_js.kotlinx.serialization.serializer_jtjczu$;
  var Long = Kotlin.Long;
  var kotlin_js_internal_LongCompanionObject = Kotlin.kotlin.js.internal.LongCompanionObject;
  var serializer_2 = $module$kotlinx_serialization_runtime_js.kotlinx.serialization.serializer_vbrujs$;
  var SerializersModule = $module$kotlinx_serialization_runtime_js.kotlinx.serialization.modules.SerializersModule_q4tcel$;
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_mqih57$;
  var ensureNotNull = Kotlin.ensureNotNull;
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
  var HashMap_init = Kotlin.kotlin.collections.HashMap_init_q3lmfv$;
  var ArrayList_init_0 = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  var equals = Kotlin.equals;
  var toString = Kotlin.toString;
  var RuntimeException_init = Kotlin.kotlin.RuntimeException_init_pdl1vj$;
  var throwCCE = Kotlin.throwCCE;
  var listOf = Kotlin.kotlin.collections.listOf_i5x0yv$;
  var LinkedHashSet_init = Kotlin.kotlin.collections.LinkedHashSet_init_287e2$;
  var NoSuchElementException_init = Kotlin.kotlin.NoSuchElementException;
  var MutableCollection = Kotlin.kotlin.collections.MutableCollection;
  var split = Kotlin.kotlin.text.split_ip8yn$;
  var replace = Kotlin.kotlin.text.replace_680rmw$;
  var SerialClassDescImpl = $module$kotlinx_serialization_runtime_js.kotlinx.serialization.internal.SerialClassDescImpl;
  var NullableSerializer = $module$kotlinx_serialization_runtime_js.kotlinx.serialization.internal.NullableSerializer;
  var ArrayListSerializer = $module$kotlinx_serialization_runtime_js.kotlinx.serialization.internal.ArrayListSerializer;
  var EnumSerializer = $module$kotlinx_serialization_runtime_js.kotlinx.serialization.internal.EnumSerializer;
  var UnknownFieldException = $module$kotlinx_serialization_runtime_js.kotlinx.serialization.UnknownFieldException;
  var internal = $module$kotlinx_serialization_runtime_js.kotlinx.serialization.internal;
  var GeneratedSerializer = $module$kotlinx_serialization_runtime_js.kotlinx.serialization.internal.GeneratedSerializer;
  var MissingFieldException = $module$kotlinx_serialization_runtime_js.kotlinx.serialization.MissingFieldException;
  var PolymorphicSerializer = $module$kotlinx_serialization_runtime_js.kotlinx.serialization.PolymorphicSerializer;
  var last = Kotlin.kotlin.collections.last_2p1efm$;
  var LinkedHashMapSerializer = $module$kotlinx_serialization_runtime_js.kotlinx.serialization.internal.LinkedHashMapSerializer;
  EventViewType.prototype = Object.create(Enum.prototype);
  EventViewType.prototype.constructor = EventViewType;
  CategoryController.prototype = Object.create(BaseController.prototype);
  CategoryController.prototype.constructor = CategoryController;
  StreamController.prototype = Object.create(BaseController.prototype);
  StreamController.prototype.constructor = StreamController;
  ChainableEvent.prototype = Object.create(Event.prototype);
  ChainableEvent.prototype.constructor = ChainableEvent;
  ApiCall.prototype = Object.create(ChainableEvent.prototype);
  ApiCall.prototype.constructor = ApiCall;
  KitApiCall.prototype = Object.create(ApiCall.prototype);
  KitApiCall.prototype.constructor = KitApiCall;
  NullObject.prototype = Object.create(ObjectValue.prototype);
  NullObject.prototype.constructor = NullObject;
  Primitive.prototype = Object.create(ObjectValue.prototype);
  Primitive.prototype.constructor = Primitive;
  EnumObject.prototype = Object.create(ObjectValue.prototype);
  EnumObject.prototype.constructor = EnumObject;
  Obj.prototype = Object.create(ObjectValue.prototype);
  Obj.prototype.constructor = Obj;
  CollectionObject.prototype = Object.create(ObjectValue.prototype);
  CollectionObject.prototype.constructor = CollectionObject;
  MapObject.prototype = Object.create(ObjectValue.prototype);
  MapObject.prototype.constructor = MapObject;
  Kit.prototype = Object.create(Event.prototype);
  Kit.prototype.constructor = Kit;
  MessageTable.prototype = Object.create(Event.prototype);
  MessageTable.prototype.constructor = MessageTable;
  MessageEvent.prototype = Object.create(ChainableEvent.prototype);
  MessageEvent.prototype.constructor = MessageEvent;
  NetworkRequest.prototype = Object.create(ChainableEvent.prototype);
  NetworkRequest.prototype.constructor = NetworkRequest;
  StateEvent.prototype = Object.create(Event.prototype);
  StateEvent.prototype.constructor = StateEvent;
  StateCurrentUser.prototype = Object.create(StateEvent.prototype);
  StateCurrentUser.prototype.constructor = StateCurrentUser;
  StateAllUsers.prototype = Object.create(StateEvent.prototype);
  StateAllUsers.prototype.constructor = StateAllUsers;
  StateAllSessions.prototype = Object.create(StateEvent.prototype);
  StateAllSessions.prototype.constructor = StateAllSessions;
  StateStatus.prototype = Object.create(StateEvent.prototype);
  StateStatus.prototype.constructor = StateStatus;
  Status.prototype = Object.create(Enum.prototype);
  Status.prototype.constructor = Status;
  ChainTitle.prototype = Object.create(Event.prototype);
  ChainTitle.prototype.constructor = ChainTitle;
  CategoryTitle.prototype = Object.create(Event.prototype);
  CategoryTitle.prototype.constructor = CategoryTitle;
  Order.prototype = Object.create(Enum.prototype);
  Order.prototype.constructor = Order;
  function EventViewType(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function EventViewType_initFields() {
    EventViewType_initFields = function () {
    };
    EventViewType$valTitle_instance = new EventViewType('valTitle', 0);
    EventViewType$valMessage_instance = new EventViewType('valMessage', 1);
    EventViewType$valMessageTable_instance = new EventViewType('valMessageTable', 2);
    EventViewType$valNetworkRequest_instance = new EventViewType('valNetworkRequest', 3);
    EventViewType$valApiCall_instance = new EventViewType('valApiCall', 4);
    EventViewType$valKit_instance = new EventViewType('valKit', 5);
    EventViewType$valStateGeneric_instance = new EventViewType('valStateGeneric', 6);
    EventViewType$valStateCurrentUser_instance = new EventViewType('valStateCurrentUser', 7);
    EventViewType$valStateList_instance = new EventViewType('valStateList', 8);
    EventViewType$valStateStatus_instance = new EventViewType('valStateStatus', 9);
    EventViewType$valChainTitle_instance = new EventViewType('valChainTitle', 10);
    EventViewType$valNext_instance = new EventViewType('valNext', 11);
    EventViewType$valUnknown_instance = new EventViewType('valUnknown', 12);
  }
  var EventViewType$valTitle_instance;
  function EventViewType$valTitle_getInstance() {
    EventViewType_initFields();
    return EventViewType$valTitle_instance;
  }
  var EventViewType$valMessage_instance;
  function EventViewType$valMessage_getInstance() {
    EventViewType_initFields();
    return EventViewType$valMessage_instance;
  }
  var EventViewType$valMessageTable_instance;
  function EventViewType$valMessageTable_getInstance() {
    EventViewType_initFields();
    return EventViewType$valMessageTable_instance;
  }
  var EventViewType$valNetworkRequest_instance;
  function EventViewType$valNetworkRequest_getInstance() {
    EventViewType_initFields();
    return EventViewType$valNetworkRequest_instance;
  }
  var EventViewType$valApiCall_instance;
  function EventViewType$valApiCall_getInstance() {
    EventViewType_initFields();
    return EventViewType$valApiCall_instance;
  }
  var EventViewType$valKit_instance;
  function EventViewType$valKit_getInstance() {
    EventViewType_initFields();
    return EventViewType$valKit_instance;
  }
  var EventViewType$valStateGeneric_instance;
  function EventViewType$valStateGeneric_getInstance() {
    EventViewType_initFields();
    return EventViewType$valStateGeneric_instance;
  }
  var EventViewType$valStateCurrentUser_instance;
  function EventViewType$valStateCurrentUser_getInstance() {
    EventViewType_initFields();
    return EventViewType$valStateCurrentUser_instance;
  }
  var EventViewType$valStateList_instance;
  function EventViewType$valStateList_getInstance() {
    EventViewType_initFields();
    return EventViewType$valStateList_instance;
  }
  var EventViewType$valStateStatus_instance;
  function EventViewType$valStateStatus_getInstance() {
    EventViewType_initFields();
    return EventViewType$valStateStatus_instance;
  }
  var EventViewType$valChainTitle_instance;
  function EventViewType$valChainTitle_getInstance() {
    EventViewType_initFields();
    return EventViewType$valChainTitle_instance;
  }
  var EventViewType$valNext_instance;
  function EventViewType$valNext_getInstance() {
    EventViewType_initFields();
    return EventViewType$valNext_instance;
  }
  var EventViewType$valUnknown_instance;
  function EventViewType$valUnknown_getInstance() {
    EventViewType_initFields();
    return EventViewType$valUnknown_instance;
  }
  EventViewType.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'EventViewType',
    interfaces: [Enum]
  };
  function EventViewType$values() {
    return [EventViewType$valTitle_getInstance(), EventViewType$valMessage_getInstance(), EventViewType$valMessageTable_getInstance(), EventViewType$valNetworkRequest_getInstance(), EventViewType$valApiCall_getInstance(), EventViewType$valKit_getInstance(), EventViewType$valStateGeneric_getInstance(), EventViewType$valStateCurrentUser_getInstance(), EventViewType$valStateList_getInstance(), EventViewType$valStateStatus_getInstance(), EventViewType$valChainTitle_getInstance(), EventViewType$valNext_getInstance(), EventViewType$valUnknown_getInstance()];
  }
  EventViewType.values = EventViewType$values;
  function EventViewType$valueOf(name) {
    switch (name) {
      case 'valTitle':
        return EventViewType$valTitle_getInstance();
      case 'valMessage':
        return EventViewType$valMessage_getInstance();
      case 'valMessageTable':
        return EventViewType$valMessageTable_getInstance();
      case 'valNetworkRequest':
        return EventViewType$valNetworkRequest_getInstance();
      case 'valApiCall':
        return EventViewType$valApiCall_getInstance();
      case 'valKit':
        return EventViewType$valKit_getInstance();
      case 'valStateGeneric':
        return EventViewType$valStateGeneric_getInstance();
      case 'valStateCurrentUser':
        return EventViewType$valStateCurrentUser_getInstance();
      case 'valStateList':
        return EventViewType$valStateList_getInstance();
      case 'valStateStatus':
        return EventViewType$valStateStatus_getInstance();
      case 'valChainTitle':
        return EventViewType$valChainTitle_getInstance();
      case 'valNext':
        return EventViewType$valNext_getInstance();
      case 'valUnknown':
        return EventViewType$valUnknown_getInstance();
      default:throwISE('No enum constant com.mparticle.shared.EventViewType.' + name);
    }
  }
  EventViewType.valueOf_61zpoe$ = EventViewType$valueOf;
  function getDtoType($receiver) {
    var tmp$;
    if (Kotlin.isType($receiver, CategoryTitle))
      tmp$ = EventViewType$valTitle_getInstance();
    else if (Kotlin.isType($receiver, NetworkRequest))
      tmp$ = EventViewType$valNetworkRequest_getInstance();
    else if (Kotlin.isType($receiver, ApiCall))
      tmp$ = EventViewType$valApiCall_getInstance();
    else if (Kotlin.isType($receiver, Kit))
      tmp$ = EventViewType$valKit_getInstance();
    else if (Kotlin.isType($receiver, MessageEvent))
      tmp$ = EventViewType$valMessage_getInstance();
    else if (Kotlin.isType($receiver, StateEvent))
      tmp$ = EventViewType$valStateGeneric_getInstance();
    else if (Kotlin.isType($receiver, MessageTable))
      tmp$ = EventViewType$valMessageTable_getInstance();
    else if (Kotlin.isType($receiver, ChainTitle))
      tmp$ = EventViewType$valChainTitle_getInstance();
    else
      tmp$ = EventViewType$valUnknown_getInstance();
    return tmp$;
  }
  function getShortName($receiver) {
    var tmp$;
    if (Kotlin.isType($receiver, NetworkRequest))
      tmp$ = 'Network';
    else if (Kotlin.isType($receiver, Kit))
      tmp$ = 'Kit';
    else if (Kotlin.isType($receiver, KitApiCall))
      tmp$ = 'Kit API';
    else if (Kotlin.isType($receiver, MessageEvent))
      tmp$ = 'DB';
    else if (Kotlin.isType($receiver, MessageTable))
      tmp$ = 'DB';
    else if (Kotlin.isType($receiver, ApiCall))
      tmp$ = 'API';
    else
      tmp$ = 'no title :(';
    return tmp$;
  }
  function putIfEmpty($receiver, key, value) {
    if (!$receiver.containsKey_11rb$(key)) {
      $receiver.put_xwzc9p$(key, value);
    }
  }
  function Serializer() {
    this.eventModule = SerializersModule(Serializer$eventModule$lambda);
  }
  Serializer.prototype.serialize_cvupbp$ = function (events) {
    var collection = new EventCollection(events);
    var json = new Json(new JsonConfiguration(void 0, void 0, void 0, void 0, void 0, true), this.eventModule);
    return json.stringify_tf03ej$(EventCollection$Companion_getInstance().serializer(), collection);
  };
  Serializer.prototype.deserialize_61zpoe$ = function (jsonString) {
    var json = new Json(new JsonConfiguration(void 0, void 0, void 0, void 0, void 0, true), this.eventModule);
    var eventCollection = json.parse_awif5v$(EventCollection$Companion_getInstance().serializer(), jsonString);
    return eventCollection.list;
  };
  function Serializer$eventModule$lambda$lambda($receiver) {
    $receiver.with_kmpi2j$(getKClass(ApiCall), ApiCall$Companion_getInstance().serializer());
    $receiver.with_kmpi2j$(getKClass(Kit), Kit$Companion_getInstance().serializer());
    $receiver.with_kmpi2j$(getKClass(KitApiCall), KitApiCall$Companion_getInstance().serializer());
    $receiver.with_kmpi2j$(getKClass(MessageEvent), MessageEvent$Companion_getInstance().serializer());
    $receiver.with_kmpi2j$(getKClass(MessageTable), MessageTable$Companion_getInstance().serializer());
    $receiver.with_kmpi2j$(getKClass(NetworkRequest), NetworkRequest$Companion_getInstance().serializer());
    $receiver.with_kmpi2j$(getKClass(StateAllSessions), StateAllSessions$Companion_getInstance().serializer());
    $receiver.with_kmpi2j$(getKClass(StateAllUsers), StateAllUsers$Companion_getInstance().serializer());
    $receiver.with_kmpi2j$(getKClass(StateCurrentUser), StateCurrentUser$Companion_getInstance().serializer());
    $receiver.with_kmpi2j$(getKClass(StateEvent), StateEvent$Companion_getInstance().serializer());
    $receiver.with_kmpi2j$(getKClass(StateStatus), StateStatus$Companion_getInstance().serializer());
    $receiver.with_kmpi2j$(getKClass(User), User$Companion_getInstance().serializer());
    return Unit;
  }
  function Serializer$eventModule$lambda$lambda_0($receiver) {
    $receiver.with_kmpi2j$(PrimitiveClasses$intClass, serializer(kotlin_js_internal_IntCompanionObject));
    $receiver.with_kmpi2j$(PrimitiveClasses$doubleClass, serializer_0(kotlin_js_internal_DoubleCompanionObject));
    $receiver.with_kmpi2j$(getKClass(ObjectArgument), ObjectArgument$Companion_getInstance().serializer());
    $receiver.with_kmpi2j$(PrimitiveClasses$booleanClass, serializer_1(kotlin_js_internal_BooleanCompanionObject));
    $receiver.with_kmpi2j$(getKClass(Long), serializer_2(kotlin_js_internal_LongCompanionObject));
    return Unit;
  }
  function Serializer$eventModule$lambda$lambda_1($receiver) {
    $receiver.with_kmpi2j$(getKClass(Primitive), Primitive$Companion_getInstance().serializer());
    $receiver.with_kmpi2j$(getKClass(EnumObject), EnumObject$Companion_getInstance().serializer());
    $receiver.with_kmpi2j$(getKClass(Obj), Obj$Companion_getInstance().serializer());
    $receiver.with_kmpi2j$(getKClass(MapObject), MapObject$Companion_getInstance().serializer());
    $receiver.with_kmpi2j$(getKClass(CollectionObject), CollectionObject$Companion_getInstance().serializer());
    $receiver.with_kmpi2j$(getKClass(NullObject), NullObject$Companion_getInstance().serializer());
    return Unit;
  }
  function Serializer$eventModule$lambda($receiver) {
    $receiver.polymorphic_myr6su$(getKClass(Event), [], Serializer$eventModule$lambda$lambda);
    $receiver.polymorphic_myr6su$(PrimitiveClasses$anyClass, [], Serializer$eventModule$lambda$lambda_0);
    $receiver.polymorphic_myr6su$(getKClass(ObjectValue), [], Serializer$eventModule$lambda$lambda_1);
    return Unit;
  }
  Serializer.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Serializer',
    interfaces: []
  };
  function ViewControllerManager() {
    ViewControllerManager$Companion_getInstance();
    this.streamController = new StreamController();
    this.categoryController = new CategoryController(ViewControllerManager$categoryController$lambda(this));
    this.activeKits_0 = HashMap_init();
    this.events_0 = ArrayList_init_0();
  }
  Object.defineProperty(ViewControllerManager.prototype, 'allEvents', {
    get: function () {
      return ArrayList_init(this.events_0);
    }
  });
  ViewControllerManager.prototype.addEvent_gaj3ty$ = function (item) {
    if (this.events_0.contains_11rb$(item)) {
      this.streamController.refreshItem_gaj3ty$(item);
      this.categoryController.refreshItem_gaj3ty$(item);
    }
     else {
      if (Kotlin.isType(item, Kit)) {
        this.activeKits_0.put_xwzc9p$(item.kitId, item);
      }
      this.events_0.add_11rb$(item);
      this.streamController.addItem_gaj3ty$(item);
      this.categoryController.addItem_gaj3ty$(item);
    }
  };
  ViewControllerManager.prototype.addEvents_61zpoe$ = function (itemsJson) {
  };
  function ViewControllerManager$Companion() {
    ViewControllerManager$Companion_instance = this;
    this.instance_0 = null;
  }
  ViewControllerManager$Companion.prototype.getInstance = function () {
    if (this.instance_0 == null) {
      this.instance_0 = new ViewControllerManager();
    }
    return ensureNotNull(this.instance_0);
  };
  ViewControllerManager$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var ViewControllerManager$Companion_instance = null;
  function ViewControllerManager$Companion_getInstance() {
    if (ViewControllerManager$Companion_instance === null) {
      new ViewControllerManager$Companion();
    }
    return ViewControllerManager$Companion_instance;
  }
  function ViewControllerManager$categoryController$lambda(this$ViewControllerManager) {
    return function (it) {
      return this$ViewControllerManager.activeKits_0.get_11rb$(it);
    };
  }
  ViewControllerManager.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ViewControllerManager',
    interfaces: []
  };
  function BaseController() {
    this.events_qjiqm0$_0 = ArrayList_init_0();
    this.refreshListeners_9iexcd$_0 = ArrayList_init_0();
    this.addItemListeners_yaujd2$_0 = ArrayList_init_0();
    this.removeListener_fb1xyf$_0 = ArrayList_init_0();
    this.listUpdatedListener_d9fm1a$_0 = ArrayList_init_0();
  }
  BaseController.prototype.registerAddedListener_52n5in$ = function (listener) {
    this.addItemListeners_yaujd2$_0.add_11rb$(listener);
  };
  BaseController.prototype.registerRefreshListener_52n5in$ = function (listener) {
    this.refreshListeners_9iexcd$_0.add_11rb$(listener);
  };
  BaseController.prototype.registerRemovedListener_nnebrl$ = function (listener) {
    this.removeListener_fb1xyf$_0.add_11rb$(listener);
  };
  BaseController.prototype.registerListUpdatedListener_1npd2e$ = function (listener) {
    this.listUpdatedListener_d9fm1a$_0.add_11rb$(listener);
  };
  BaseController.prototype.getEvents = function () {
    return ArrayList_init(this.events_qjiqm0$_0);
  };
  BaseController.prototype.onAdded_juysyl$ = function (objectList, position, item) {
    this.events_qjiqm0$_0 = objectList;
    var tmp$;
    tmp$ = this.addItemListeners_yaujd2$_0.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      element != null ? element(position, item) : null;
    }
  };
  BaseController.prototype.onRefreshed_ogev6x$ = function (item, position) {
    if (position === void 0)
      position = this.getEvents().indexOf_11rb$(item);
    if (position != null) {
      var tmp$;
      tmp$ = this.refreshListeners_9iexcd$_0.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        element != null ? element(position, item) : null;
      }
    }
  };
  BaseController.prototype.onRemoved_vpuwqz$ = function (objectList, position, count) {
    if (count === void 0)
      count = 1;
    this.events_qjiqm0$_0 = objectList;
    var tmp$;
    tmp$ = this.removeListener_fb1xyf$_0.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      element != null ? element(position, count) : null;
    }
  };
  BaseController.prototype.onListUpdate_cvupbp$ = function (list) {
    this.events_qjiqm0$_0 = list;
    var tmp$;
    tmp$ = this.listUpdatedListener_d9fm1a$_0.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      element != null ? element(ArrayList_init(list)) : null;
    }
  };
  BaseController.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'BaseController',
    interfaces: []
  };
  function CategoryController(activeKitsCallback) {
    BaseController.call(this);
    this.activeKitsCallback_0 = activeKitsCallback;
    this.titleStoredMessages_0 = 'Database State';
    this.titleNetworkRequest_0 = 'Network Usage';
    this.titleApiCall_0 = 'Api Usage';
    this.titleKit_0 = 'Kit State';
    this.titleState_0 = 'SDK State';
    this.objectMap_0 = HashMap_init();
    this.messageTableMap_0 = HashMap_init();
    this.categoryTitles_8be2vx$ = listOf([new CategoryTitle(this.titleState_0, EventViewType$valStateGeneric_getInstance(), void 0, Order$Custom_getInstance()), new CategoryTitle(this.titleApiCall_0, EventViewType$valApiCall_getInstance()), new CategoryTitle(this.titleKit_0, EventViewType$valKit_getInstance(), void 0, Order$Alphbetical_getInstance()), new CategoryTitle(this.titleNetworkRequest_0, EventViewType$valNetworkRequest_getInstance()), new CategoryTitle(this.titleStoredMessages_0, EventViewType$valMessageTable_getInstance(), void 0, Order$Alphbetical_getInstance())]);
    var $receiver = this.objectMap_0;
    putIfEmpty($receiver, EventViewType$valTitle_getInstance(), LinkedHashSet_init());
    putIfEmpty($receiver, EventViewType$valStateGeneric_getInstance(), LinkedHashSet_init());
    putIfEmpty($receiver, EventViewType$valApiCall_getInstance(), LinkedHashSet_init());
    putIfEmpty($receiver, EventViewType$valNetworkRequest_getInstance(), LinkedHashSet_init());
    putIfEmpty($receiver, EventViewType$valMessage_getInstance(), LinkedHashSet_init());
    putIfEmpty($receiver, EventViewType$valMessageTable_getInstance(), LinkedHashSet_init());
    putIfEmpty($receiver, EventViewType$valKit_getInstance(), LinkedHashSet_init());
    var tmp$;
    tmp$ = this.categoryTitles_8be2vx$.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      this.addItem_gaj3ty$(element);
    }
    this.onExpandCallback_0 = CategoryController$onExpandCallback$lambda(this);
  }
  CategoryController.prototype.addItem_gaj3ty$ = function (item) {
    this.addItem_s5s5xa$(item, true);
  };
  CategoryController.prototype.refreshItem_gaj3ty$ = function (item) {
    this.onRefreshed_ogev6x$(item);
  };
  function CategoryController$addItem$lambda(this$CategoryController, closure$obj) {
    return function (it) {
      this$CategoryController.onExpandCallback_0(it, closure$obj);
      return Unit;
    };
  }
  CategoryController.prototype.addItem_s5s5xa$ = function (obj, new_0) {
    if (new_0 === void 0)
      new_0 = true;
    var tmp$, tmp$_0, tmp$_1, tmp$_2;
    if (Kotlin.isType(obj, NetworkRequest))
      this.addItemToList_0(this.titleNetworkRequest_0, obj, new_0);
    else if (Kotlin.isType(obj, KitApiCall)) {
      if ((tmp$ = this.activeKitsCallback_0(obj.kitId)) != null) {
        tmp$.apiCalls.add_11rb$(obj);
        this.onRefreshed_ogev6x$(tmp$);
      }
    }
     else if (Kotlin.isType(obj, ApiCall))
      this.addItemToList_0(this.titleApiCall_0, obj, new_0);
    else if (Kotlin.isType(obj, Kit))
      this.addItemToList_0(this.titleKit_0, obj, new_0);
    else if (Kotlin.isType(obj, MessageEvent)) {
      var newMessageTable = {v: false};
      if (!this.messageTableMap_0.containsKey_11rb$(obj.name)) {
        newMessageTable.v = true;
        this.messageTableMap_0.put_xwzc9p$(obj.name, new MessageTable(obj.name));
      }
      if ((tmp$_0 = this.messageTableMap_0.get_11rb$(obj.name)) != null) {
        tmp$_0.messages.put_xwzc9p$(obj, new Mutable(obj.bodyExpanded));
        this.addItemToList_0(this.titleStoredMessages_0, tmp$_0, newMessageTable.v);
        this.onRefreshed_ogev6x$(tmp$_0);
      }
    }
     else if (Kotlin.isType(obj, CategoryTitle)) {
      obj.onExpand_8be2vx$ = CategoryController$addItem$lambda(this, obj);
      var tmp$_3;
      if ((tmp$_1 = this.objectMap_0.get_11rb$(EventViewType$valTitle_getInstance())) != null) {
        var firstOrNull$result;
        firstOrNull$break: do {
          var tmp$_4;
          tmp$_4 = tmp$_1.iterator();
          while (tmp$_4.hasNext()) {
            var element = tmp$_4.next();
            if (Kotlin.isType(element, CategoryTitle) && equals(element.name, obj.name)) {
              firstOrNull$result = element;
              break firstOrNull$break;
            }
          }
          firstOrNull$result = null;
        }
         while (false);
        tmp$_3 = firstOrNull$result;
      }
       else
        tmp$_3 = null;
      if (tmp$_3 == null) {
        (tmp$_2 = this.objectMap_0.get_11rb$(EventViewType$valTitle_getInstance())) != null ? tmp$_2.add_11rb$(obj) : null;
      }
      var objects = this.getEvents();
      var firstOrNull$result_0;
      firstOrNull$break: do {
        var tmp$_5;
        tmp$_5 = objects.iterator();
        while (tmp$_5.hasNext()) {
          var element_0 = tmp$_5.next();
          if (Kotlin.isType(element_0, CategoryTitle) && equals(element_0.name, obj.name)) {
            firstOrNull$result_0 = element_0;
            break firstOrNull$break;
          }
        }
        firstOrNull$result_0 = null;
      }
       while (false);
      if (firstOrNull$result_0 == null) {
        objects.add_11rb$(obj);
      }
      this.onListUpdate_cvupbp$(objects);
    }
     else if (Kotlin.isType(obj, StateEvent))
      this.addItemToList_0(this.titleState_0, obj, new_0);
    else if (Kotlin.isType(obj, MessageTable))
      this.addItemToList_0(this.titleStoredMessages_0, obj, false);
    else
      throw RuntimeException_init('Unimplemented Item title: ' + toString(Kotlin.getKClassFromExpression(obj).simpleName));
  };
  CategoryController.prototype.addItemToList_0 = function (title, obj, new_0) {
    if (new_0 === void 0)
      new_0 = null;
    var tmp$, tmp$_0, tmp$_1, tmp$_2;
    if (new_0 === true)
      ensureNotNull(this.objectMap_0.get_11rb$(getDtoType(obj))).add_11rb$(obj);
    var tmp$_3;
    if ((tmp$ = this.objectMap_0.get_11rb$(EventViewType$valTitle_getInstance())) != null) {
      var first$result;
      first$break: do {
        var tmp$_4;
        tmp$_4 = tmp$.iterator();
        while (tmp$_4.hasNext()) {
          var element = tmp$_4.next();
          if (Kotlin.isType(element, CategoryTitle) && equals(element.name, title)) {
            first$result = element;
            break first$break;
          }
        }
        throw new NoSuchElementException_init('Collection contains no element matching the predicate.');
      }
       while (false);
      tmp$_3 = first$result;
    }
     else
      tmp$_3 = null;
    var titleDto = tmp$_3;
    if ((Kotlin.isType(tmp$_0 = titleDto, CategoryTitle) ? tmp$_0 : throwCCE()).expanded) {
      var objects = this.getEvents();
      var indexToAdd = {v: objects.indexOf_11rb$(titleDto) + 1 | 0};
      var $receiver = objects.subList_vux9f0$(indexToAdd.v, objects.size);
      var indexOfFirst$result;
      indexOfFirst$break: do {
        var tmp$_5;
        var index = 0;
        tmp$_5 = $receiver.iterator();
        while (tmp$_5.hasNext()) {
          var item = tmp$_5.next();
          if (Kotlin.isType(item, CategoryTitle)) {
            indexOfFirst$result = index;
            break indexOfFirst$break;
          }
          index = index + 1 | 0;
        }
        indexOfFirst$result = -1;
      }
       while (false);
      var it = indexOfFirst$result;
      var block$result;
      if (it >= 0) {
        block$result = it + indexToAdd.v | 0;
      }
       else {
        block$result = objects.size;
      }
      var maxIndex = block$result;
      if (indexToAdd.v < objects.size) {
        loop_label: switch (titleDto.order.name) {
          case 'Chronological_Recent_First':
            break loop_label;
          case 'Custom':
            if (Kotlin.isType(obj, StateEvent)) {
              var $receiver_0 = objects.subList_vux9f0$(indexToAdd.v, maxIndex + 1 | 0);
              var indexOfFirst$result_0;
              indexOfFirst$break: do {
                var tmp$_6;
                var index_0 = 0;
                tmp$_6 = $receiver_0.iterator();
                while (tmp$_6.hasNext()) {
                  var item_0 = tmp$_6.next();
                  var predicate$result;
                  if (Kotlin.isType(item_0, StateEvent)) {
                    predicate$result = obj.priority > item_0.priority;
                  }
                   else {
                    predicate$result = false;
                  }
                  if (predicate$result) {
                    indexOfFirst$result_0 = index_0;
                    break indexOfFirst$break;
                  }
                  index_0 = index_0 + 1 | 0;
                }
                indexOfFirst$result_0 = -1;
              }
               while (false);
              var prioritizedIndex = indexOfFirst$result_0;
              if (prioritizedIndex > 0) {
                indexToAdd.v = prioritizedIndex;
              }
               else {
                indexToAdd.v = maxIndex;
              }
            }

            break loop_label;
          case 'Alphbetical':
            var $receiver_1 = objects.subList_vux9f0$(indexToAdd.v, maxIndex);
            var indexOfFirst$result_1;
            indexOfFirst$break: do {
              var tmp$_7;
              var index_1 = 0;
              tmp$_7 = $receiver_1.iterator();
              while (tmp$_7.hasNext()) {
                var item_1 = tmp$_7.next();
                if (Kotlin.compareTo(obj.name, item_1.name) < 0) {
                  indexOfFirst$result_1 = index_1;
                  break indexOfFirst$break;
                }
                index_1 = index_1 + 1 | 0;
              }
              indexOfFirst$result_1 = -1;
            }
             while (false);
            var alphabeticIndex = indexOfFirst$result_1;
            if (alphabeticIndex >= 0) {
              indexToAdd.v = indexToAdd.v + alphabeticIndex | 0;
            }
             else {
              indexToAdd.v = maxIndex;
            }

            break loop_label;
        }
      }
      if (!objects.contains_11rb$(obj)) {
        titleDto.count = titleDto.count + 1 | 0;
        objects.add_wxm5ur$(indexToAdd.v, obj);
        this.onAdded_juysyl$(objects, indexToAdd.v, obj);
      }
       else {
        this.onRefreshed_ogev6x$(obj, indexToAdd.v);
      }
    }
    titleDto.count = (tmp$_2 = (tmp$_1 = this.objectMap_0.get_11rb$(titleDto.itemType)) != null ? tmp$_1.size : null) != null ? tmp$_2 : 0;
    this.onRefreshed_ogev6x$(titleDto);
  };
  function CategoryController$onExpandCallback$lambda(this$CategoryController) {
    return function (expanded, obj) {
      var tmp$, tmp$_0, tmp$_1;
      obj.expanded = expanded;
      obj.count = (tmp$_0 = (tmp$ = this$CategoryController.objectMap_0.get_11rb$(obj.itemType)) != null ? tmp$.size : null) != null ? tmp$_0 : 0;
      if (obj.expanded) {
        if ((tmp$_1 = this$CategoryController.objectMap_0.get_11rb$(obj.itemType)) != null) {
          var this$CategoryController_0 = this$CategoryController;
          var tmp$_2;
          tmp$_2 = ArrayList_init(tmp$_1).iterator();
          while (tmp$_2.hasNext()) {
            var element = tmp$_2.next();
            this$CategoryController_0.addItem_s5s5xa$(element, false);
          }
        }
      }
       else {
        var found = {v: false};
        var objects = this$CategoryController.getEvents();
        var $receiver = objects.subList_vux9f0$(objects.indexOf_11rb$(obj) + 1 | 0, objects.size);
        var this$CategoryController_1 = this$CategoryController;
        var count = {v: 0};
        var tmp$_3;
        tmp$_3 = ArrayList_init($receiver).iterator();
        while (tmp$_3.hasNext()) {
          var element_0 = tmp$_3.next();
          if (Kotlin.isType(element_0, CategoryTitle)) {
            found.v = true;
          }
           else {
            if (!found.v) {
              count.v = count.v + 1 | 0;
              var tmp$_4;
              (Kotlin.isType(tmp$_4 = objects, MutableCollection) ? tmp$_4 : throwCCE()).remove_11rb$(element_0);
            }
          }
        }
        if (count.v > 0) {
          this$CategoryController_1.onRemoved_vpuwqz$(objects, this$CategoryController_1.getEvents().indexOf_11rb$(obj) + 1 | 0, count.v);
        }
      }
      this$CategoryController.onRefreshed_ogev6x$(obj);
      return Unit;
    };
  }
  CategoryController.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'CategoryController',
    interfaces: [BaseController]
  };
  function StreamController() {
    BaseController.call(this);
    this.eventCountLimit = 100;
  }
  StreamController.prototype.addItem_gaj3ty$ = function (item) {
    var obj = {v: item};
    if ((Kotlin.isType(obj.v, CategoryTitle) || getDtoType(obj.v) === EventViewType$valStateGeneric_getInstance()) && !Kotlin.isType(obj.v, KitApiCall)) {
      return;
    }
    if (Kotlin.isType(obj.v, MessageEvent)) {
      var $receiver = new MessageTable(obj.v.name);
      var tmp$;
      $receiver.messages.put_xwzc9p$(Kotlin.isType(tmp$ = obj.v, MessageEvent) ? tmp$ : throwCCE(), new Mutable(false));
      obj.v = $receiver;
    }
    var objects = {v: this.getEvents()};
    if (objects.v.size > this.eventCountLimit) {
      this.onRemoved_vpuwqz$(objects.v.subList_vux9f0$(0, this.eventCountLimit - 1 | 0), this.eventCountLimit - 1 | 0, objects.v.size - this.eventCountLimit | 0);
    }
    objects.v = this.getEvents();
    var $receiver_0 = objects.v;
    var indexOfFirst$result;
    indexOfFirst$break: do {
      var tmp$_0;
      var index = 0;
      tmp$_0 = $receiver_0.iterator();
      while (tmp$_0.hasNext()) {
        var item_0 = tmp$_0.next();
        if (obj.v.createdTime.compareTo_11rb$(item_0.createdTime) > 0) {
          indexOfFirst$result = index;
          break indexOfFirst$break;
        }
        index = index + 1 | 0;
      }
      indexOfFirst$result = -1;
    }
     while (false);
    var it = indexOfFirst$result;
    if (it > 0) {
      objects.v.add_wxm5ur$(it - 1 | 0, obj.v);
      this.onAdded_juysyl$(objects.v, it - 1 | 0, obj.v);
    }
     else {
      objects.v.add_wxm5ur$(0, obj.v);
      this.onAdded_juysyl$(objects.v, 0, obj.v);
    }
  };
  StreamController.prototype.refreshItem_gaj3ty$ = function (item) {
    this.onRefreshed_ogev6x$(item);
  };
  StreamController.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'StreamController',
    interfaces: [BaseController]
  };
  function ApiCall(endpoint, arguments_0, timeSent, objectArgument, expanded, id, status) {
    ApiCall$Companion_getInstance();
    if (objectArgument === void 0)
      objectArgument = null;
    if (expanded === void 0)
      expanded = false;
    if (status === void 0)
      status = null;
    ChainableEvent.call(this);
    this.endpoint = endpoint;
    this.arguments = arguments_0;
    this.timeSent = timeSent;
    this.objectArgument = objectArgument;
    this.expanded = expanded;
    this.id_clo5o5$_0 = id;
    this.status_xh002$_0 = status;
    this.name_zbg7ut$_0 = this.endpoint;
  }
  Object.defineProperty(ApiCall.prototype, 'id', {
    get: function () {
      return this.id_clo5o5$_0;
    },
    set: function (id) {
      this.id_clo5o5$_0 = id;
    }
  });
  Object.defineProperty(ApiCall.prototype, 'status', {
    get: function () {
      return this.status_xh002$_0;
    },
    set: function (status) {
      this.status_xh002$_0 = status;
    }
  });
  Object.defineProperty(ApiCall.prototype, 'name', {
    get: function () {
      return this.name_zbg7ut$_0;
    }
  });
  ApiCall.prototype.copy = function () {
    return new ApiCall(this.name, this.arguments, this.timeSent, this.objectArgument, this.expanded, this.id, this.status);
  };
  ApiCall.prototype.getMethodName = function () {
    return replace(split(this.endpoint, ['.']).get_za3lpa$(1), '()', '');
  };
  ApiCall.prototype.getClassName = function () {
    return split(this.endpoint, ['.']).get_za3lpa$(0);
  };
  function ApiCall$Companion() {
    ApiCall$Companion_instance = this;
  }
  ApiCall$Companion.prototype.serializer = function () {
    return ApiCall$$serializer_getInstance();
  };
  ApiCall$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var ApiCall$Companion_instance = null;
  function ApiCall$Companion_getInstance() {
    if (ApiCall$Companion_instance === null) {
      new ApiCall$Companion();
    }
    return ApiCall$Companion_instance;
  }
  function ApiCall$$serializer() {
    this.descriptor_sfegyf$_0 = new SerialClassDescImpl('com.mparticle.shared.events.ApiCall', this);
    this.descriptor.addElement_ivxn3r$('endpoint', false);
    this.descriptor.addElement_ivxn3r$('arguments', false);
    this.descriptor.addElement_ivxn3r$('timeSent', false);
    this.descriptor.addElement_ivxn3r$('objectArgument', true);
    this.descriptor.addElement_ivxn3r$('expanded', true);
    this.descriptor.addElement_ivxn3r$('id', false);
    this.descriptor.addElement_ivxn3r$('status', true);
    this.descriptor.addElement_ivxn3r$('name', true);
    ApiCall$$serializer_instance = this;
  }
  Object.defineProperty(ApiCall$$serializer.prototype, 'descriptor', {
    get: function () {
      return this.descriptor_sfegyf$_0;
    }
  });
  ApiCall$$serializer.prototype.serialize_awe97i$ = function (encoder, obj) {
    var output = encoder.beginStructure_r0sa6z$(this.descriptor, []);
    output.encodeStringElement_bgm7zs$(this.descriptor, 0, obj.endpoint);
    output.encodeSerializableElement_blecud$(this.descriptor, 1, new ArrayListSerializer(ObjectArgument$$serializer_getInstance()), obj.arguments);
    output.encodeLongElement_a3zgoj$(this.descriptor, 2, obj.timeSent);
    if (!equals(obj.objectArgument, null) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 3))
      output.encodeNullableSerializableElement_orpvvi$(this.descriptor, 3, ObjectArgument$$serializer_getInstance(), obj.objectArgument);
    if (!equals(obj.expanded, false) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 4))
      output.encodeBooleanElement_w1b0nl$(this.descriptor, 4, obj.expanded);
    output.encodeIntElement_4wpqag$(this.descriptor, 5, obj.id);
    if (!equals(obj.status, null) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 6))
      output.encodeNullableSerializableElement_orpvvi$(this.descriptor, 6, new EnumSerializer(getKClass(Status)), obj.status);
    if (!equals(obj.name, this.endpoint) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 7))
      output.encodeStringElement_bgm7zs$(this.descriptor, 7, obj.name);
    output.endStructure_qatsm0$(this.descriptor);
  };
  ApiCall$$serializer.prototype.deserialize_nts5qn$ = function (decoder) {
    var index, readAll = false;
    var bitMask0 = 0;
    var local0
    , local1
    , local2
    , local3
    , local4
    , local5
    , local6
    , local7;
    var input = decoder.beginStructure_r0sa6z$(this.descriptor, []);
    loopLabel: while (true) {
      index = input.decodeElementIndex_qatsm0$(this.descriptor);
      switch (index) {
        case -2:
          readAll = true;
        case 0:
          local0 = input.decodeStringElement_3zr2iy$(this.descriptor, 0);
          bitMask0 |= 1;
          if (!readAll)
            break;
        case 1:
          local1 = (bitMask0 & 2) === 0 ? input.decodeSerializableElement_s44l7r$(this.descriptor, 1, new ArrayListSerializer(ObjectArgument$$serializer_getInstance())) : input.updateSerializableElement_ehubvl$(this.descriptor, 1, new ArrayListSerializer(ObjectArgument$$serializer_getInstance()), local1);
          bitMask0 |= 2;
          if (!readAll)
            break;
        case 2:
          local2 = input.decodeLongElement_3zr2iy$(this.descriptor, 2);
          bitMask0 |= 4;
          if (!readAll)
            break;
        case 3:
          local3 = (bitMask0 & 8) === 0 ? input.decodeNullableSerializableElement_cwlm4k$(this.descriptor, 3, ObjectArgument$$serializer_getInstance()) : input.updateNullableSerializableElement_u33s02$(this.descriptor, 3, ObjectArgument$$serializer_getInstance(), local3);
          bitMask0 |= 8;
          if (!readAll)
            break;
        case 4:
          local4 = input.decodeBooleanElement_3zr2iy$(this.descriptor, 4);
          bitMask0 |= 16;
          if (!readAll)
            break;
        case 5:
          local5 = input.decodeIntElement_3zr2iy$(this.descriptor, 5);
          bitMask0 |= 32;
          if (!readAll)
            break;
        case 6:
          local6 = (bitMask0 & 64) === 0 ? input.decodeNullableSerializableElement_cwlm4k$(this.descriptor, 6, new EnumSerializer(getKClass(Status))) : input.updateNullableSerializableElement_u33s02$(this.descriptor, 6, new EnumSerializer(getKClass(Status)), local6);
          bitMask0 |= 64;
          if (!readAll)
            break;
        case 7:
          local7 = input.decodeStringElement_3zr2iy$(this.descriptor, 7);
          bitMask0 |= 128;
          if (!readAll)
            break;
        case -1:
          break loopLabel;
        default:throw new UnknownFieldException(index);
      }
    }
    input.endStructure_qatsm0$(this.descriptor);
    return ApiCall_init(bitMask0, local0, local1, local2, local3, local4, local5, local6, local7, null);
  };
  ApiCall$$serializer.prototype.childSerializers = function () {
    return [internal.StringSerializer, new ArrayListSerializer(ObjectArgument$$serializer_getInstance()), internal.LongSerializer, new NullableSerializer(ObjectArgument$$serializer_getInstance()), internal.BooleanSerializer, internal.IntSerializer, new NullableSerializer(new EnumSerializer(getKClass(Status))), internal.StringSerializer];
  };
  ApiCall$$serializer.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: '$serializer',
    interfaces: [GeneratedSerializer]
  };
  var ApiCall$$serializer_instance = null;
  function ApiCall$$serializer_getInstance() {
    if (ApiCall$$serializer_instance === null) {
      new ApiCall$$serializer();
    }
    return ApiCall$$serializer_instance;
  }
  function ApiCall_init(seen1, endpoint, arguments_0, timeSent, objectArgument, expanded, id, status, name, serializationConstructorMarker) {
    var $this = serializationConstructorMarker || Object.create(ApiCall.prototype);
    ChainableEvent.call($this);
    if ((seen1 & 1) === 0)
      throw new MissingFieldException('endpoint');
    else
      $this.endpoint = endpoint;
    if ((seen1 & 2) === 0)
      throw new MissingFieldException('arguments');
    else
      $this.arguments = arguments_0;
    if ((seen1 & 4) === 0)
      throw new MissingFieldException('timeSent');
    else
      $this.timeSent = timeSent;
    if ((seen1 & 8) === 0)
      $this.objectArgument = null;
    else
      $this.objectArgument = objectArgument;
    if ((seen1 & 16) === 0)
      $this.expanded = false;
    else
      $this.expanded = expanded;
    if ((seen1 & 32) === 0)
      throw new MissingFieldException('id');
    else
      $this.id_clo5o5$_0 = id;
    if ((seen1 & 64) === 0)
      $this.status_xh002$_0 = null;
    else
      $this.status_xh002$_0 = status;
    if ((seen1 & 128) === 0)
      $this.name_zbg7ut$_0 = $this.endpoint;
    else
      $this.name_zbg7ut$_0 = name;
    return $this;
  }
  ApiCall.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ApiCall',
    interfaces: [ChainableEvent]
  };
  function KitApiCall(kitId, endpointName, kitObjectArguments, kitTimeSent, kitExpanded, id, status) {
    KitApiCall$Companion_getInstance();
    if (kitExpanded === void 0)
      kitExpanded = false;
    ApiCall.call(this, endpointName, kitObjectArguments, kitTimeSent, null, kitExpanded, id, status);
    this.kitId = kitId;
    this.endpointName = endpointName;
    this.kitObjectArguments = kitObjectArguments;
    this.kitTimeSent = kitTimeSent;
    this.kitExpanded = kitExpanded;
    this.id_orldgn$_0 = id;
    this.status_ajgay8$_0 = status;
  }
  Object.defineProperty(KitApiCall.prototype, 'id', {
    get: function () {
      return this.id_orldgn$_0;
    },
    set: function (id) {
      this.id_orldgn$_0 = id;
    }
  });
  Object.defineProperty(KitApiCall.prototype, 'status', {
    get: function () {
      return this.status_ajgay8$_0;
    },
    set: function (status) {
      this.status_ajgay8$_0 = status;
    }
  });
  KitApiCall.prototype.copy = function () {
    var tmp$;
    return new KitApiCall(this.kitId, this.name, this.arguments, this.timeSent, this.expanded, this.id, (tmp$ = this.status) != null ? tmp$ : Status$Red_getInstance());
  };
  function KitApiCall$Companion() {
    KitApiCall$Companion_instance = this;
  }
  KitApiCall$Companion.prototype.serializer = function () {
    return KitApiCall$$serializer_getInstance();
  };
  KitApiCall$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var KitApiCall$Companion_instance = null;
  function KitApiCall$Companion_getInstance() {
    if (KitApiCall$Companion_instance === null) {
      new KitApiCall$Companion();
    }
    return KitApiCall$Companion_instance;
  }
  function KitApiCall$$serializer() {
    this.descriptor_7k20yj$_0 = new SerialClassDescImpl('com.mparticle.shared.events.KitApiCall', this);
    this.descriptor.addElement_ivxn3r$('endpoint', false);
    this.descriptor.addElement_ivxn3r$('arguments', false);
    this.descriptor.addElement_ivxn3r$('timeSent', false);
    this.descriptor.addElement_ivxn3r$('objectArgument', true);
    this.descriptor.addElement_ivxn3r$('expanded', true);
    this.descriptor.addElement_ivxn3r$('id', false);
    this.descriptor.addElement_ivxn3r$('status', true);
    this.descriptor.addElement_ivxn3r$('name', true);
    this.descriptor.addElement_ivxn3r$('kitId', false);
    this.descriptor.addElement_ivxn3r$('endpointName', false);
    this.descriptor.addElement_ivxn3r$('kitObjectArguments', false);
    this.descriptor.addElement_ivxn3r$('kitTimeSent', false);
    this.descriptor.addElement_ivxn3r$('kitExpanded', true);
    this.descriptor.addElement_ivxn3r$('kit_id', false);
    this.descriptor.addElement_ivxn3r$('kit_status', false);
    KitApiCall$$serializer_instance = this;
  }
  Object.defineProperty(KitApiCall$$serializer.prototype, 'descriptor', {
    get: function () {
      return this.descriptor_7k20yj$_0;
    }
  });
  KitApiCall$$serializer.prototype.serialize_awe97i$ = function (encoder, obj) {
    var output = encoder.beginStructure_r0sa6z$(this.descriptor, []);
    output.encodeStringElement_bgm7zs$(this.descriptor, 0, obj.endpoint);
    output.encodeSerializableElement_blecud$(this.descriptor, 1, new ArrayListSerializer(ObjectArgument$$serializer_getInstance()), obj.arguments);
    output.encodeLongElement_a3zgoj$(this.descriptor, 2, obj.timeSent);
    if (!equals(obj.objectArgument, null) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 3))
      output.encodeNullableSerializableElement_orpvvi$(this.descriptor, 3, ObjectArgument$$serializer_getInstance(), obj.objectArgument);
    if (!equals(obj.expanded, false) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 4))
      output.encodeBooleanElement_w1b0nl$(this.descriptor, 4, obj.expanded);
    output.encodeIntElement_4wpqag$(this.descriptor, 5, obj.id);
    if (!equals(obj.status, null) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 6))
      output.encodeNullableSerializableElement_orpvvi$(this.descriptor, 6, new EnumSerializer(getKClass(Status)), obj.status);
    if (!equals(obj.name, this.endpoint) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 7))
      output.encodeStringElement_bgm7zs$(this.descriptor, 7, obj.name);
    output.encodeIntElement_4wpqag$(this.descriptor, 8, obj.kitId);
    output.encodeStringElement_bgm7zs$(this.descriptor, 9, obj.endpointName);
    output.encodeSerializableElement_blecud$(this.descriptor, 10, new ArrayListSerializer(ObjectArgument$$serializer_getInstance()), obj.kitObjectArguments);
    output.encodeLongElement_a3zgoj$(this.descriptor, 11, obj.kitTimeSent);
    if (!equals(obj.kitExpanded, false) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 12))
      output.encodeBooleanElement_w1b0nl$(this.descriptor, 12, obj.kitExpanded);
    output.encodeIntElement_4wpqag$(this.descriptor, 13, obj.id);
    output.encodeNullableSerializableElement_orpvvi$(this.descriptor, 14, new EnumSerializer(getKClass(Status)), obj.status);
    output.endStructure_qatsm0$(this.descriptor);
  };
  KitApiCall$$serializer.prototype.deserialize_nts5qn$ = function (decoder) {
    var index, readAll = false;
    var bitMask0 = 0;
    var local0
    , local1
    , local2
    , local3
    , local4
    , local5
    , local6
    , local7
    , local8
    , local9
    , local10
    , local11
    , local12
    , local13
    , local14;
    var input = decoder.beginStructure_r0sa6z$(this.descriptor, []);
    loopLabel: while (true) {
      index = input.decodeElementIndex_qatsm0$(this.descriptor);
      switch (index) {
        case -2:
          readAll = true;
        case 0:
          local0 = input.decodeStringElement_3zr2iy$(this.descriptor, 0);
          bitMask0 |= 1;
          if (!readAll)
            break;
        case 1:
          local1 = (bitMask0 & 2) === 0 ? input.decodeSerializableElement_s44l7r$(this.descriptor, 1, new ArrayListSerializer(ObjectArgument$$serializer_getInstance())) : input.updateSerializableElement_ehubvl$(this.descriptor, 1, new ArrayListSerializer(ObjectArgument$$serializer_getInstance()), local1);
          bitMask0 |= 2;
          if (!readAll)
            break;
        case 2:
          local2 = input.decodeLongElement_3zr2iy$(this.descriptor, 2);
          bitMask0 |= 4;
          if (!readAll)
            break;
        case 3:
          local3 = (bitMask0 & 8) === 0 ? input.decodeNullableSerializableElement_cwlm4k$(this.descriptor, 3, ObjectArgument$$serializer_getInstance()) : input.updateNullableSerializableElement_u33s02$(this.descriptor, 3, ObjectArgument$$serializer_getInstance(), local3);
          bitMask0 |= 8;
          if (!readAll)
            break;
        case 4:
          local4 = input.decodeBooleanElement_3zr2iy$(this.descriptor, 4);
          bitMask0 |= 16;
          if (!readAll)
            break;
        case 5:
          local5 = input.decodeIntElement_3zr2iy$(this.descriptor, 5);
          bitMask0 |= 32;
          if (!readAll)
            break;
        case 6:
          local6 = (bitMask0 & 64) === 0 ? input.decodeNullableSerializableElement_cwlm4k$(this.descriptor, 6, new EnumSerializer(getKClass(Status))) : input.updateNullableSerializableElement_u33s02$(this.descriptor, 6, new EnumSerializer(getKClass(Status)), local6);
          bitMask0 |= 64;
          if (!readAll)
            break;
        case 7:
          local7 = input.decodeStringElement_3zr2iy$(this.descriptor, 7);
          bitMask0 |= 128;
          if (!readAll)
            break;
        case 8:
          local8 = input.decodeIntElement_3zr2iy$(this.descriptor, 8);
          bitMask0 |= 256;
          if (!readAll)
            break;
        case 9:
          local9 = input.decodeStringElement_3zr2iy$(this.descriptor, 9);
          bitMask0 |= 512;
          if (!readAll)
            break;
        case 10:
          local10 = (bitMask0 & 1024) === 0 ? input.decodeSerializableElement_s44l7r$(this.descriptor, 10, new ArrayListSerializer(ObjectArgument$$serializer_getInstance())) : input.updateSerializableElement_ehubvl$(this.descriptor, 10, new ArrayListSerializer(ObjectArgument$$serializer_getInstance()), local10);
          bitMask0 |= 1024;
          if (!readAll)
            break;
        case 11:
          local11 = input.decodeLongElement_3zr2iy$(this.descriptor, 11);
          bitMask0 |= 2048;
          if (!readAll)
            break;
        case 12:
          local12 = input.decodeBooleanElement_3zr2iy$(this.descriptor, 12);
          bitMask0 |= 4096;
          if (!readAll)
            break;
        case 13:
          local13 = input.decodeIntElement_3zr2iy$(this.descriptor, 13);
          bitMask0 |= 8192;
          if (!readAll)
            break;
        case 14:
          local14 = (bitMask0 & 16384) === 0 ? input.decodeNullableSerializableElement_cwlm4k$(this.descriptor, 14, new EnumSerializer(getKClass(Status))) : input.updateNullableSerializableElement_u33s02$(this.descriptor, 14, new EnumSerializer(getKClass(Status)), local14);
          bitMask0 |= 16384;
          if (!readAll)
            break;
        case -1:
          break loopLabel;
        default:throw new UnknownFieldException(index);
      }
    }
    input.endStructure_qatsm0$(this.descriptor);
    return KitApiCall_init(bitMask0, local0, local1, local2, local3, local4, local5, local6, local7, local8, local9, local10, local11, local12, local13, local14, null);
  };
  KitApiCall$$serializer.prototype.childSerializers = function () {
    return [internal.StringSerializer, new ArrayListSerializer(ObjectArgument$$serializer_getInstance()), internal.LongSerializer, new NullableSerializer(ObjectArgument$$serializer_getInstance()), internal.BooleanSerializer, internal.IntSerializer, new NullableSerializer(new EnumSerializer(getKClass(Status))), internal.StringSerializer, internal.IntSerializer, internal.StringSerializer, new ArrayListSerializer(ObjectArgument$$serializer_getInstance()), internal.LongSerializer, internal.BooleanSerializer, internal.IntSerializer, new NullableSerializer(new EnumSerializer(getKClass(Status)))];
  };
  KitApiCall$$serializer.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: '$serializer',
    interfaces: [GeneratedSerializer]
  };
  var KitApiCall$$serializer_instance = null;
  function KitApiCall$$serializer_getInstance() {
    if (KitApiCall$$serializer_instance === null) {
      new KitApiCall$$serializer();
    }
    return KitApiCall$$serializer_instance;
  }
  function KitApiCall_init(seen1, endpoint, arguments_0, timeSent, objectArgument, expanded, id, status, name, kitId, endpointName, kitObjectArguments, kitTimeSent, kitExpanded, id_0, status_0, serializationConstructorMarker) {
    var $this = serializationConstructorMarker || Object.create(KitApiCall.prototype);
    $this = ApiCall_init(seen1, endpoint, arguments_0, timeSent, objectArgument, expanded, id, status, name, $this);
    if ((seen1 & 256) === 0)
      throw new MissingFieldException('kitId');
    else
      $this.kitId = kitId;
    if ((seen1 & 512) === 0)
      throw new MissingFieldException('endpointName');
    else
      $this.endpointName = endpointName;
    if ((seen1 & 1024) === 0)
      throw new MissingFieldException('kitObjectArguments');
    else
      $this.kitObjectArguments = kitObjectArguments;
    if ((seen1 & 2048) === 0)
      throw new MissingFieldException('kitTimeSent');
    else
      $this.kitTimeSent = kitTimeSent;
    if ((seen1 & 4096) === 0)
      $this.kitExpanded = false;
    else
      $this.kitExpanded = kitExpanded;
    if ((seen1 & 8192) === 0)
      throw new MissingFieldException('kit_id');
    else
      $this.id_orldgn$_0 = id_0;
    if ((seen1 & 16384) === 0)
      throw new MissingFieldException('kit_status');
    else
      $this.status_ajgay8$_0 = status_0;
    return $this;
  }
  KitApiCall.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'KitApiCall',
    interfaces: [ApiCall]
  };
  function ObjectArgument(fullClassName, value, id) {
    ObjectArgument$Companion_getInstance();
    if (id === void 0)
      id = null;
    this.fullClassName = fullClassName;
    this.value = value;
    this.id = id;
    this.className = last(split(this.fullClassName, ['.']));
  }
  function ObjectArgument$Companion() {
    ObjectArgument$Companion_instance = this;
  }
  ObjectArgument$Companion.prototype.serializer = function () {
    return ObjectArgument$$serializer_getInstance();
  };
  ObjectArgument$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var ObjectArgument$Companion_instance = null;
  function ObjectArgument$Companion_getInstance() {
    if (ObjectArgument$Companion_instance === null) {
      new ObjectArgument$Companion();
    }
    return ObjectArgument$Companion_instance;
  }
  function ObjectArgument$$serializer() {
    this.descriptor_506s9h$_0 = new SerialClassDescImpl('com.mparticle.shared.events.ObjectArgument', this);
    this.descriptor.addElement_ivxn3r$('fullClassName', false);
    this.descriptor.addElement_ivxn3r$('value', false);
    this.descriptor.addElement_ivxn3r$('id', true);
    this.descriptor.addElement_ivxn3r$('className', true);
    ObjectArgument$$serializer_instance = this;
  }
  Object.defineProperty(ObjectArgument$$serializer.prototype, 'descriptor', {
    get: function () {
      return this.descriptor_506s9h$_0;
    }
  });
  ObjectArgument$$serializer.prototype.serialize_awe97i$ = function (encoder, obj) {
    var output = encoder.beginStructure_r0sa6z$(this.descriptor, []);
    output.encodeStringElement_bgm7zs$(this.descriptor, 0, obj.fullClassName);
    output.encodeSerializableElement_blecud$(this.descriptor, 1, new PolymorphicSerializer(getKClass(ObjectValue)), obj.value);
    if (!equals(obj.id, null) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 2))
      output.encodeNullableSerializableElement_orpvvi$(this.descriptor, 2, internal.IntSerializer, obj.id);
    if (!equals(obj.className, last(split(this.fullClassName, ['.']))) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 3))
      output.encodeStringElement_bgm7zs$(this.descriptor, 3, obj.className);
    output.endStructure_qatsm0$(this.descriptor);
  };
  ObjectArgument$$serializer.prototype.deserialize_nts5qn$ = function (decoder) {
    var index, readAll = false;
    var bitMask0 = 0;
    var local0
    , local1
    , local2
    , local3;
    var input = decoder.beginStructure_r0sa6z$(this.descriptor, []);
    loopLabel: while (true) {
      index = input.decodeElementIndex_qatsm0$(this.descriptor);
      switch (index) {
        case -2:
          readAll = true;
        case 0:
          local0 = input.decodeStringElement_3zr2iy$(this.descriptor, 0);
          bitMask0 |= 1;
          if (!readAll)
            break;
        case 1:
          local1 = (bitMask0 & 2) === 0 ? input.decodeSerializableElement_s44l7r$(this.descriptor, 1, new PolymorphicSerializer(getKClass(ObjectValue))) : input.updateSerializableElement_ehubvl$(this.descriptor, 1, new PolymorphicSerializer(getKClass(ObjectValue)), local1);
          bitMask0 |= 2;
          if (!readAll)
            break;
        case 2:
          local2 = (bitMask0 & 4) === 0 ? input.decodeNullableSerializableElement_cwlm4k$(this.descriptor, 2, internal.IntSerializer) : input.updateNullableSerializableElement_u33s02$(this.descriptor, 2, internal.IntSerializer, local2);
          bitMask0 |= 4;
          if (!readAll)
            break;
        case 3:
          local3 = input.decodeStringElement_3zr2iy$(this.descriptor, 3);
          bitMask0 |= 8;
          if (!readAll)
            break;
        case -1:
          break loopLabel;
        default:throw new UnknownFieldException(index);
      }
    }
    input.endStructure_qatsm0$(this.descriptor);
    return ObjectArgument_init(bitMask0, local0, local1, local2, local3, null);
  };
  ObjectArgument$$serializer.prototype.childSerializers = function () {
    return [internal.StringSerializer, new PolymorphicSerializer(getKClass(ObjectValue)), new NullableSerializer(internal.IntSerializer), internal.StringSerializer];
  };
  ObjectArgument$$serializer.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: '$serializer',
    interfaces: [GeneratedSerializer]
  };
  var ObjectArgument$$serializer_instance = null;
  function ObjectArgument$$serializer_getInstance() {
    if (ObjectArgument$$serializer_instance === null) {
      new ObjectArgument$$serializer();
    }
    return ObjectArgument$$serializer_instance;
  }
  function ObjectArgument_init(seen1, fullClassName, value, id, className, serializationConstructorMarker) {
    var $this = serializationConstructorMarker || Object.create(ObjectArgument.prototype);
    if ((seen1 & 1) === 0)
      throw new MissingFieldException('fullClassName');
    else
      $this.fullClassName = fullClassName;
    if ((seen1 & 2) === 0)
      throw new MissingFieldException('value');
    else
      $this.value = value;
    if ((seen1 & 4) === 0)
      $this.id = null;
    else
      $this.id = id;
    if ((seen1 & 8) === 0)
      $this.className = last(split($this.fullClassName, ['.']));
    else
      $this.className = className;
    return $this;
  }
  ObjectArgument.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ObjectArgument',
    interfaces: []
  };
  ObjectArgument.prototype.component1 = function () {
    return this.fullClassName;
  };
  ObjectArgument.prototype.component2 = function () {
    return this.value;
  };
  ObjectArgument.prototype.component3 = function () {
    return this.id;
  };
  ObjectArgument.prototype.copy_zdy9a7$ = function (fullClassName, value, id) {
    return new ObjectArgument(fullClassName === void 0 ? this.fullClassName : fullClassName, value === void 0 ? this.value : value, id === void 0 ? this.id : id);
  };
  ObjectArgument.prototype.toString = function () {
    return 'ObjectArgument(fullClassName=' + Kotlin.toString(this.fullClassName) + (', value=' + Kotlin.toString(this.value)) + (', id=' + Kotlin.toString(this.id)) + ')';
  };
  ObjectArgument.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.fullClassName) | 0;
    result = result * 31 + Kotlin.hashCode(this.value) | 0;
    result = result * 31 + Kotlin.hashCode(this.id) | 0;
    return result;
  };
  ObjectArgument.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.fullClassName, other.fullClassName) && Kotlin.equals(this.value, other.value) && Kotlin.equals(this.id, other.id)))));
  };
  function ObjectValue() {
  }
  ObjectValue.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ObjectValue',
    interfaces: []
  };
  function NullObject() {
    NullObject$Companion_getInstance();
    ObjectValue.call(this);
  }
  function NullObject$Companion() {
    NullObject$Companion_instance = this;
  }
  NullObject$Companion.prototype.serializer = function () {
    return NullObject$$serializer_getInstance();
  };
  NullObject$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var NullObject$Companion_instance = null;
  function NullObject$Companion_getInstance() {
    if (NullObject$Companion_instance === null) {
      new NullObject$Companion();
    }
    return NullObject$Companion_instance;
  }
  function NullObject$$serializer() {
    this.descriptor_t3bn6n$_0 = new SerialClassDescImpl('com.mparticle.shared.events.NullObject', this);
    NullObject$$serializer_instance = this;
  }
  Object.defineProperty(NullObject$$serializer.prototype, 'descriptor', {
    get: function () {
      return this.descriptor_t3bn6n$_0;
    }
  });
  NullObject$$serializer.prototype.serialize_awe97i$ = function (encoder, obj) {
    var output = encoder.beginStructure_r0sa6z$(this.descriptor, []);
    output.endStructure_qatsm0$(this.descriptor);
  };
  NullObject$$serializer.prototype.deserialize_nts5qn$ = function (decoder) {
    var index, readAll = false;
    var bitMask0 = 0;
    var input = decoder.beginStructure_r0sa6z$(this.descriptor, []);
    loopLabel: while (true) {
      index = input.decodeElementIndex_qatsm0$(this.descriptor);
      switch (index) {
        case -2:
          readAll = true;
        case -1:
          break loopLabel;
        default:throw new UnknownFieldException(index);
      }
    }
    input.endStructure_qatsm0$(this.descriptor);
    return NullObject_init(bitMask0, null);
  };
  NullObject$$serializer.prototype.childSerializers = function () {
    return [];
  };
  NullObject$$serializer.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: '$serializer',
    interfaces: [GeneratedSerializer]
  };
  var NullObject$$serializer_instance = null;
  function NullObject$$serializer_getInstance() {
    if (NullObject$$serializer_instance === null) {
      new NullObject$$serializer();
    }
    return NullObject$$serializer_instance;
  }
  function NullObject_init(seen1, serializationConstructorMarker) {
    var $this = serializationConstructorMarker || Object.create(NullObject.prototype);
    ObjectValue.call($this);
    return $this;
  }
  NullObject.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'NullObject',
    interfaces: [ObjectValue]
  };
  function Primitive(value) {
    Primitive$Companion_getInstance();
    ObjectValue.call(this);
    this.value = value;
  }
  function Primitive$Companion() {
    Primitive$Companion_instance = this;
  }
  Primitive$Companion.prototype.serializer = function () {
    return Primitive$$serializer_getInstance();
  };
  Primitive$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var Primitive$Companion_instance = null;
  function Primitive$Companion_getInstance() {
    if (Primitive$Companion_instance === null) {
      new Primitive$Companion();
    }
    return Primitive$Companion_instance;
  }
  function Primitive$$serializer() {
    this.descriptor_4yp7nc$_0 = new SerialClassDescImpl('com.mparticle.shared.events.Primitive', this);
    this.descriptor.addElement_ivxn3r$('value', false);
    Primitive$$serializer_instance = this;
  }
  Object.defineProperty(Primitive$$serializer.prototype, 'descriptor', {
    get: function () {
      return this.descriptor_4yp7nc$_0;
    }
  });
  Primitive$$serializer.prototype.serialize_awe97i$ = function (encoder, obj) {
    var output = encoder.beginStructure_r0sa6z$(this.descriptor, []);
    output.encodeSerializableElement_blecud$(this.descriptor, 0, new PolymorphicSerializer(PrimitiveClasses$anyClass), obj.value);
    output.endStructure_qatsm0$(this.descriptor);
  };
  Primitive$$serializer.prototype.deserialize_nts5qn$ = function (decoder) {
    var index, readAll = false;
    var bitMask0 = 0;
    var local0;
    var input = decoder.beginStructure_r0sa6z$(this.descriptor, []);
    loopLabel: while (true) {
      index = input.decodeElementIndex_qatsm0$(this.descriptor);
      switch (index) {
        case -2:
          readAll = true;
        case 0:
          local0 = (bitMask0 & 1) === 0 ? input.decodeSerializableElement_s44l7r$(this.descriptor, 0, new PolymorphicSerializer(PrimitiveClasses$anyClass)) : input.updateSerializableElement_ehubvl$(this.descriptor, 0, new PolymorphicSerializer(PrimitiveClasses$anyClass), local0);
          bitMask0 |= 1;
          if (!readAll)
            break;
        case -1:
          break loopLabel;
        default:throw new UnknownFieldException(index);
      }
    }
    input.endStructure_qatsm0$(this.descriptor);
    return Primitive_init(bitMask0, local0, null);
  };
  Primitive$$serializer.prototype.childSerializers = function () {
    return [new PolymorphicSerializer(PrimitiveClasses$anyClass)];
  };
  Primitive$$serializer.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: '$serializer',
    interfaces: [GeneratedSerializer]
  };
  var Primitive$$serializer_instance = null;
  function Primitive$$serializer_getInstance() {
    if (Primitive$$serializer_instance === null) {
      new Primitive$$serializer();
    }
    return Primitive$$serializer_instance;
  }
  function Primitive_init(seen1, value, serializationConstructorMarker) {
    var $this = serializationConstructorMarker || Object.create(Primitive.prototype);
    ObjectValue.call($this);
    if ((seen1 & 1) === 0)
      throw new MissingFieldException('value');
    else
      $this.value = value;
    return $this;
  }
  Primitive.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Primitive',
    interfaces: [ObjectValue]
  };
  function EnumObject(name) {
    EnumObject$Companion_getInstance();
    ObjectValue.call(this);
    this.name = name;
  }
  function EnumObject$Companion() {
    EnumObject$Companion_instance = this;
  }
  EnumObject$Companion.prototype.serializer = function () {
    return EnumObject$$serializer_getInstance();
  };
  EnumObject$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var EnumObject$Companion_instance = null;
  function EnumObject$Companion_getInstance() {
    if (EnumObject$Companion_instance === null) {
      new EnumObject$Companion();
    }
    return EnumObject$Companion_instance;
  }
  function EnumObject$$serializer() {
    this.descriptor_m2p59j$_0 = new SerialClassDescImpl('com.mparticle.shared.events.EnumObject', this);
    this.descriptor.addElement_ivxn3r$('name', false);
    EnumObject$$serializer_instance = this;
  }
  Object.defineProperty(EnumObject$$serializer.prototype, 'descriptor', {
    get: function () {
      return this.descriptor_m2p59j$_0;
    }
  });
  EnumObject$$serializer.prototype.serialize_awe97i$ = function (encoder, obj) {
    var output = encoder.beginStructure_r0sa6z$(this.descriptor, []);
    output.encodeStringElement_bgm7zs$(this.descriptor, 0, obj.name);
    output.endStructure_qatsm0$(this.descriptor);
  };
  EnumObject$$serializer.prototype.deserialize_nts5qn$ = function (decoder) {
    var index, readAll = false;
    var bitMask0 = 0;
    var local0;
    var input = decoder.beginStructure_r0sa6z$(this.descriptor, []);
    loopLabel: while (true) {
      index = input.decodeElementIndex_qatsm0$(this.descriptor);
      switch (index) {
        case -2:
          readAll = true;
        case 0:
          local0 = input.decodeStringElement_3zr2iy$(this.descriptor, 0);
          bitMask0 |= 1;
          if (!readAll)
            break;
        case -1:
          break loopLabel;
        default:throw new UnknownFieldException(index);
      }
    }
    input.endStructure_qatsm0$(this.descriptor);
    return EnumObject_init(bitMask0, local0, null);
  };
  EnumObject$$serializer.prototype.childSerializers = function () {
    return [internal.StringSerializer];
  };
  EnumObject$$serializer.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: '$serializer',
    interfaces: [GeneratedSerializer]
  };
  var EnumObject$$serializer_instance = null;
  function EnumObject$$serializer_getInstance() {
    if (EnumObject$$serializer_instance === null) {
      new EnumObject$$serializer();
    }
    return EnumObject$$serializer_instance;
  }
  function EnumObject_init(seen1, name, serializationConstructorMarker) {
    var $this = serializationConstructorMarker || Object.create(EnumObject.prototype);
    ObjectValue.call($this);
    if ((seen1 & 1) === 0)
      throw new MissingFieldException('name');
    else
      $this.name = name;
    return $this;
  }
  EnumObject.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'EnumObject',
    interfaces: [ObjectValue]
  };
  function Obj(fields) {
    Obj$Companion_getInstance();
    ObjectValue.call(this);
    this.fields = fields;
  }
  function Obj$Companion() {
    Obj$Companion_instance = this;
  }
  Obj$Companion.prototype.serializer = function () {
    return Obj$$serializer_getInstance();
  };
  Obj$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var Obj$Companion_instance = null;
  function Obj$Companion_getInstance() {
    if (Obj$Companion_instance === null) {
      new Obj$Companion();
    }
    return Obj$Companion_instance;
  }
  function Obj$$serializer() {
    this.descriptor_tkdq88$_0 = new SerialClassDescImpl('com.mparticle.shared.events.Obj', this);
    this.descriptor.addElement_ivxn3r$('fields', false);
    Obj$$serializer_instance = this;
  }
  Object.defineProperty(Obj$$serializer.prototype, 'descriptor', {
    get: function () {
      return this.descriptor_tkdq88$_0;
    }
  });
  Obj$$serializer.prototype.serialize_awe97i$ = function (encoder, obj) {
    var output = encoder.beginStructure_r0sa6z$(this.descriptor, []);
    output.encodeSerializableElement_blecud$(this.descriptor, 0, new ArrayListSerializer(FieldObject$$serializer_getInstance()), obj.fields);
    output.endStructure_qatsm0$(this.descriptor);
  };
  Obj$$serializer.prototype.deserialize_nts5qn$ = function (decoder) {
    var index, readAll = false;
    var bitMask0 = 0;
    var local0;
    var input = decoder.beginStructure_r0sa6z$(this.descriptor, []);
    loopLabel: while (true) {
      index = input.decodeElementIndex_qatsm0$(this.descriptor);
      switch (index) {
        case -2:
          readAll = true;
        case 0:
          local0 = (bitMask0 & 1) === 0 ? input.decodeSerializableElement_s44l7r$(this.descriptor, 0, new ArrayListSerializer(FieldObject$$serializer_getInstance())) : input.updateSerializableElement_ehubvl$(this.descriptor, 0, new ArrayListSerializer(FieldObject$$serializer_getInstance()), local0);
          bitMask0 |= 1;
          if (!readAll)
            break;
        case -1:
          break loopLabel;
        default:throw new UnknownFieldException(index);
      }
    }
    input.endStructure_qatsm0$(this.descriptor);
    return Obj_init(bitMask0, local0, null);
  };
  Obj$$serializer.prototype.childSerializers = function () {
    return [new ArrayListSerializer(FieldObject$$serializer_getInstance())];
  };
  Obj$$serializer.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: '$serializer',
    interfaces: [GeneratedSerializer]
  };
  var Obj$$serializer_instance = null;
  function Obj$$serializer_getInstance() {
    if (Obj$$serializer_instance === null) {
      new Obj$$serializer();
    }
    return Obj$$serializer_instance;
  }
  function Obj_init(seen1, fields, serializationConstructorMarker) {
    var $this = serializationConstructorMarker || Object.create(Obj.prototype);
    ObjectValue.call($this);
    if ((seen1 & 1) === 0)
      throw new MissingFieldException('fields');
    else
      $this.fields = fields;
    return $this;
  }
  Obj.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Obj',
    interfaces: [ObjectValue]
  };
  function CollectionObject(values) {
    CollectionObject$Companion_getInstance();
    ObjectValue.call(this);
    this.values = values;
  }
  function CollectionObject$Companion() {
    CollectionObject$Companion_instance = this;
  }
  CollectionObject$Companion.prototype.serializer = function () {
    return CollectionObject$$serializer_getInstance();
  };
  CollectionObject$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var CollectionObject$Companion_instance = null;
  function CollectionObject$Companion_getInstance() {
    if (CollectionObject$Companion_instance === null) {
      new CollectionObject$Companion();
    }
    return CollectionObject$Companion_instance;
  }
  function CollectionObject$$serializer() {
    this.descriptor_uxu22y$_0 = new SerialClassDescImpl('com.mparticle.shared.events.CollectionObject', this);
    this.descriptor.addElement_ivxn3r$('values', false);
    CollectionObject$$serializer_instance = this;
  }
  Object.defineProperty(CollectionObject$$serializer.prototype, 'descriptor', {
    get: function () {
      return this.descriptor_uxu22y$_0;
    }
  });
  CollectionObject$$serializer.prototype.serialize_awe97i$ = function (encoder, obj) {
    var output = encoder.beginStructure_r0sa6z$(this.descriptor, []);
    output.encodeSerializableElement_blecud$(this.descriptor, 0, new ArrayListSerializer(ObjectArgument$$serializer_getInstance()), obj.values);
    output.endStructure_qatsm0$(this.descriptor);
  };
  CollectionObject$$serializer.prototype.deserialize_nts5qn$ = function (decoder) {
    var index, readAll = false;
    var bitMask0 = 0;
    var local0;
    var input = decoder.beginStructure_r0sa6z$(this.descriptor, []);
    loopLabel: while (true) {
      index = input.decodeElementIndex_qatsm0$(this.descriptor);
      switch (index) {
        case -2:
          readAll = true;
        case 0:
          local0 = (bitMask0 & 1) === 0 ? input.decodeSerializableElement_s44l7r$(this.descriptor, 0, new ArrayListSerializer(ObjectArgument$$serializer_getInstance())) : input.updateSerializableElement_ehubvl$(this.descriptor, 0, new ArrayListSerializer(ObjectArgument$$serializer_getInstance()), local0);
          bitMask0 |= 1;
          if (!readAll)
            break;
        case -1:
          break loopLabel;
        default:throw new UnknownFieldException(index);
      }
    }
    input.endStructure_qatsm0$(this.descriptor);
    return CollectionObject_init(bitMask0, local0, null);
  };
  CollectionObject$$serializer.prototype.childSerializers = function () {
    return [new ArrayListSerializer(ObjectArgument$$serializer_getInstance())];
  };
  CollectionObject$$serializer.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: '$serializer',
    interfaces: [GeneratedSerializer]
  };
  var CollectionObject$$serializer_instance = null;
  function CollectionObject$$serializer_getInstance() {
    if (CollectionObject$$serializer_instance === null) {
      new CollectionObject$$serializer();
    }
    return CollectionObject$$serializer_instance;
  }
  function CollectionObject_init(seen1, values, serializationConstructorMarker) {
    var $this = serializationConstructorMarker || Object.create(CollectionObject.prototype);
    ObjectValue.call($this);
    if ((seen1 & 1) === 0)
      throw new MissingFieldException('values');
    else
      $this.values = values;
    return $this;
  }
  CollectionObject.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'CollectionObject',
    interfaces: [ObjectValue]
  };
  function MapObject(valuesMap) {
    MapObject$Companion_getInstance();
    ObjectValue.call(this);
    this.valuesMap = valuesMap;
  }
  function MapObject$Companion() {
    MapObject$Companion_instance = this;
  }
  MapObject$Companion.prototype.serializer = function () {
    return MapObject$$serializer_getInstance();
  };
  MapObject$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var MapObject$Companion_instance = null;
  function MapObject$Companion_getInstance() {
    if (MapObject$Companion_instance === null) {
      new MapObject$Companion();
    }
    return MapObject$Companion_instance;
  }
  function MapObject$$serializer() {
    this.descriptor_fn37ik$_0 = new SerialClassDescImpl('com.mparticle.shared.events.MapObject', this);
    this.descriptor.addElement_ivxn3r$('valuesMap', false);
    MapObject$$serializer_instance = this;
  }
  Object.defineProperty(MapObject$$serializer.prototype, 'descriptor', {
    get: function () {
      return this.descriptor_fn37ik$_0;
    }
  });
  MapObject$$serializer.prototype.serialize_awe97i$ = function (encoder, obj) {
    var output = encoder.beginStructure_r0sa6z$(this.descriptor, []);
    output.encodeSerializableElement_blecud$(this.descriptor, 0, new LinkedHashMapSerializer(ObjectArgument$$serializer_getInstance(), ObjectArgument$$serializer_getInstance()), obj.valuesMap);
    output.endStructure_qatsm0$(this.descriptor);
  };
  MapObject$$serializer.prototype.deserialize_nts5qn$ = function (decoder) {
    var index, readAll = false;
    var bitMask0 = 0;
    var local0;
    var input = decoder.beginStructure_r0sa6z$(this.descriptor, []);
    loopLabel: while (true) {
      index = input.decodeElementIndex_qatsm0$(this.descriptor);
      switch (index) {
        case -2:
          readAll = true;
        case 0:
          local0 = (bitMask0 & 1) === 0 ? input.decodeSerializableElement_s44l7r$(this.descriptor, 0, new LinkedHashMapSerializer(ObjectArgument$$serializer_getInstance(), ObjectArgument$$serializer_getInstance())) : input.updateSerializableElement_ehubvl$(this.descriptor, 0, new LinkedHashMapSerializer(ObjectArgument$$serializer_getInstance(), ObjectArgument$$serializer_getInstance()), local0);
          bitMask0 |= 1;
          if (!readAll)
            break;
        case -1:
          break loopLabel;
        default:throw new UnknownFieldException(index);
      }
    }
    input.endStructure_qatsm0$(this.descriptor);
    return MapObject_init(bitMask0, local0, null);
  };
  MapObject$$serializer.prototype.childSerializers = function () {
    return [new LinkedHashMapSerializer(ObjectArgument$$serializer_getInstance(), ObjectArgument$$serializer_getInstance())];
  };
  MapObject$$serializer.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: '$serializer',
    interfaces: [GeneratedSerializer]
  };
  var MapObject$$serializer_instance = null;
  function MapObject$$serializer_getInstance() {
    if (MapObject$$serializer_instance === null) {
      new MapObject$$serializer();
    }
    return MapObject$$serializer_instance;
  }
  function MapObject_init(seen1, valuesMap, serializationConstructorMarker) {
    var $this = serializationConstructorMarker || Object.create(MapObject.prototype);
    ObjectValue.call($this);
    if ((seen1 & 1) === 0)
      throw new MissingFieldException('valuesMap');
    else
      $this.valuesMap = valuesMap;
    return $this;
  }
  MapObject.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'MapObject',
    interfaces: [ObjectValue]
  };
  function FieldObject(access, fieldName, objectArgument, isMethod) {
    FieldObject$Companion_getInstance();
    this.access = access;
    this.fieldName = fieldName;
    this.objectArgument = objectArgument;
    this.isMethod = isMethod;
  }
  function FieldObject$Companion() {
    FieldObject$Companion_instance = this;
    this.PRIVATE = 0;
    this.PACKAGE_PRIVATE = 1;
    this.PROTECTED = 2;
    this.PUBLIC = 3;
  }
  FieldObject$Companion.prototype.serializer = function () {
    return FieldObject$$serializer_getInstance();
  };
  FieldObject$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var FieldObject$Companion_instance = null;
  function FieldObject$Companion_getInstance() {
    if (FieldObject$Companion_instance === null) {
      new FieldObject$Companion();
    }
    return FieldObject$Companion_instance;
  }
  function FieldObject$$serializer() {
    this.descriptor_bnn4yy$_0 = new SerialClassDescImpl('com.mparticle.shared.events.FieldObject', this);
    this.descriptor.addElement_ivxn3r$('access', false);
    this.descriptor.addElement_ivxn3r$('fieldName', false);
    this.descriptor.addElement_ivxn3r$('objectArgument', false);
    this.descriptor.addElement_ivxn3r$('isMethod', false);
    FieldObject$$serializer_instance = this;
  }
  Object.defineProperty(FieldObject$$serializer.prototype, 'descriptor', {
    get: function () {
      return this.descriptor_bnn4yy$_0;
    }
  });
  FieldObject$$serializer.prototype.serialize_awe97i$ = function (encoder, obj) {
    var output = encoder.beginStructure_r0sa6z$(this.descriptor, []);
    output.encodeIntElement_4wpqag$(this.descriptor, 0, obj.access);
    output.encodeStringElement_bgm7zs$(this.descriptor, 1, obj.fieldName);
    output.encodeSerializableElement_blecud$(this.descriptor, 2, ObjectArgument$$serializer_getInstance(), obj.objectArgument);
    output.encodeBooleanElement_w1b0nl$(this.descriptor, 3, obj.isMethod);
    output.endStructure_qatsm0$(this.descriptor);
  };
  FieldObject$$serializer.prototype.deserialize_nts5qn$ = function (decoder) {
    var index, readAll = false;
    var bitMask0 = 0;
    var local0
    , local1
    , local2
    , local3;
    var input = decoder.beginStructure_r0sa6z$(this.descriptor, []);
    loopLabel: while (true) {
      index = input.decodeElementIndex_qatsm0$(this.descriptor);
      switch (index) {
        case -2:
          readAll = true;
        case 0:
          local0 = input.decodeIntElement_3zr2iy$(this.descriptor, 0);
          bitMask0 |= 1;
          if (!readAll)
            break;
        case 1:
          local1 = input.decodeStringElement_3zr2iy$(this.descriptor, 1);
          bitMask0 |= 2;
          if (!readAll)
            break;
        case 2:
          local2 = (bitMask0 & 4) === 0 ? input.decodeSerializableElement_s44l7r$(this.descriptor, 2, ObjectArgument$$serializer_getInstance()) : input.updateSerializableElement_ehubvl$(this.descriptor, 2, ObjectArgument$$serializer_getInstance(), local2);
          bitMask0 |= 4;
          if (!readAll)
            break;
        case 3:
          local3 = input.decodeBooleanElement_3zr2iy$(this.descriptor, 3);
          bitMask0 |= 8;
          if (!readAll)
            break;
        case -1:
          break loopLabel;
        default:throw new UnknownFieldException(index);
      }
    }
    input.endStructure_qatsm0$(this.descriptor);
    return FieldObject_init(bitMask0, local0, local1, local2, local3, null);
  };
  FieldObject$$serializer.prototype.childSerializers = function () {
    return [internal.IntSerializer, internal.StringSerializer, ObjectArgument$$serializer_getInstance(), internal.BooleanSerializer];
  };
  FieldObject$$serializer.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: '$serializer',
    interfaces: [GeneratedSerializer]
  };
  var FieldObject$$serializer_instance = null;
  function FieldObject$$serializer_getInstance() {
    if (FieldObject$$serializer_instance === null) {
      new FieldObject$$serializer();
    }
    return FieldObject$$serializer_instance;
  }
  function FieldObject_init(seen1, access, fieldName, objectArgument, isMethod, serializationConstructorMarker) {
    var $this = serializationConstructorMarker || Object.create(FieldObject.prototype);
    if ((seen1 & 1) === 0)
      throw new MissingFieldException('access');
    else
      $this.access = access;
    if ((seen1 & 2) === 0)
      throw new MissingFieldException('fieldName');
    else
      $this.fieldName = fieldName;
    if ((seen1 & 4) === 0)
      throw new MissingFieldException('objectArgument');
    else
      $this.objectArgument = objectArgument;
    if ((seen1 & 8) === 0)
      throw new MissingFieldException('isMethod');
    else
      $this.isMethod = isMethod;
    return $this;
  }
  FieldObject.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'FieldObject',
    interfaces: []
  };
  function EventCollection(list) {
    EventCollection$Companion_getInstance();
    this.list = list;
  }
  function EventCollection$Companion() {
    EventCollection$Companion_instance = this;
  }
  EventCollection$Companion.prototype.serializer = function () {
    return EventCollection$$serializer_getInstance();
  };
  EventCollection$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var EventCollection$Companion_instance = null;
  function EventCollection$Companion_getInstance() {
    if (EventCollection$Companion_instance === null) {
      new EventCollection$Companion();
    }
    return EventCollection$Companion_instance;
  }
  function EventCollection$$serializer() {
    this.descriptor_r8pj3d$_0 = new SerialClassDescImpl('com.mparticle.shared.events.EventCollection', this);
    this.descriptor.addElement_ivxn3r$('list', false);
    EventCollection$$serializer_instance = this;
  }
  Object.defineProperty(EventCollection$$serializer.prototype, 'descriptor', {
    get: function () {
      return this.descriptor_r8pj3d$_0;
    }
  });
  EventCollection$$serializer.prototype.serialize_awe97i$ = function (encoder, obj) {
    var output = encoder.beginStructure_r0sa6z$(this.descriptor, []);
    output.encodeSerializableElement_blecud$(this.descriptor, 0, new ArrayListSerializer(new PolymorphicSerializer(getKClass(Event))), obj.list);
    output.endStructure_qatsm0$(this.descriptor);
  };
  EventCollection$$serializer.prototype.deserialize_nts5qn$ = function (decoder) {
    var index, readAll = false;
    var bitMask0 = 0;
    var local0;
    var input = decoder.beginStructure_r0sa6z$(this.descriptor, []);
    loopLabel: while (true) {
      index = input.decodeElementIndex_qatsm0$(this.descriptor);
      switch (index) {
        case -2:
          readAll = true;
        case 0:
          local0 = (bitMask0 & 1) === 0 ? input.decodeSerializableElement_s44l7r$(this.descriptor, 0, new ArrayListSerializer(new PolymorphicSerializer(getKClass(Event)))) : input.updateSerializableElement_ehubvl$(this.descriptor, 0, new ArrayListSerializer(new PolymorphicSerializer(getKClass(Event))), local0);
          bitMask0 |= 1;
          if (!readAll)
            break;
        case -1:
          break loopLabel;
        default:throw new UnknownFieldException(index);
      }
    }
    input.endStructure_qatsm0$(this.descriptor);
    return EventCollection_init(bitMask0, local0, null);
  };
  EventCollection$$serializer.prototype.childSerializers = function () {
    return [new ArrayListSerializer(new PolymorphicSerializer(getKClass(Event)))];
  };
  EventCollection$$serializer.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: '$serializer',
    interfaces: [GeneratedSerializer]
  };
  var EventCollection$$serializer_instance = null;
  function EventCollection$$serializer_getInstance() {
    if (EventCollection$$serializer_instance === null) {
      new EventCollection$$serializer();
    }
    return EventCollection$$serializer_instance;
  }
  function EventCollection_init(seen1, list, serializationConstructorMarker) {
    var $this = serializationConstructorMarker || Object.create(EventCollection.prototype);
    if ((seen1 & 1) === 0)
      throw new MissingFieldException('list');
    else
      $this.list = list;
    return $this;
  }
  EventCollection.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'EventCollection',
    interfaces: []
  };
  function Event() {
    this.createdTime_1p7r5f$_0 = (new PlatformApis()).getTimestamp();
  }
  Object.defineProperty(Event.prototype, 'createdTime', {
    get: function () {
      return this.createdTime_1p7r5f$_0;
    }
  });
  Event.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Event',
    interfaces: []
  };
  function ChainableEvent() {
    Event.call(this);
  }
  ChainableEvent.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ChainableEvent',
    interfaces: [Event]
  };
  function Kit(kitId, name, status, expanded, apiCalls, errorMessage) {
    Kit$Companion_getInstance();
    if (expanded === void 0)
      expanded = false;
    if (apiCalls === void 0)
      apiCalls = ArrayList_init_0();
    if (errorMessage === void 0)
      errorMessage = null;
    Event.call(this);
    this.kitId = kitId;
    this.name_19tu2l$_0 = name;
    this.status = status;
    this.expanded = expanded;
    this.apiCalls = apiCalls;
    this.errorMessage = errorMessage;
  }
  Object.defineProperty(Kit.prototype, 'name', {
    get: function () {
      return this.name_19tu2l$_0;
    }
  });
  function Kit$Companion() {
    Kit$Companion_instance = this;
  }
  Kit$Companion.prototype.serializer = function () {
    return Kit$$serializer_getInstance();
  };
  Kit$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var Kit$Companion_instance = null;
  function Kit$Companion_getInstance() {
    if (Kit$Companion_instance === null) {
      new Kit$Companion();
    }
    return Kit$Companion_instance;
  }
  function Kit$$serializer() {
    this.descriptor_iu0x09$_0 = new SerialClassDescImpl('com.mparticle.shared.events.Kit', this);
    this.descriptor.addElement_ivxn3r$('kitId', false);
    this.descriptor.addElement_ivxn3r$('name', false);
    this.descriptor.addElement_ivxn3r$('status', false);
    this.descriptor.addElement_ivxn3r$('expanded', true);
    this.descriptor.addElement_ivxn3r$('apiCalls', true);
    this.descriptor.addElement_ivxn3r$('errorMessage', true);
    Kit$$serializer_instance = this;
  }
  Object.defineProperty(Kit$$serializer.prototype, 'descriptor', {
    get: function () {
      return this.descriptor_iu0x09$_0;
    }
  });
  Kit$$serializer.prototype.serialize_awe97i$ = function (encoder, obj) {
    var output = encoder.beginStructure_r0sa6z$(this.descriptor, []);
    output.encodeIntElement_4wpqag$(this.descriptor, 0, obj.kitId);
    output.encodeStringElement_bgm7zs$(this.descriptor, 1, obj.name);
    output.encodeSerializableElement_blecud$(this.descriptor, 2, new EnumSerializer(getKClass(Status)), obj.status);
    if (!equals(obj.expanded, false) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 3))
      output.encodeBooleanElement_w1b0nl$(this.descriptor, 3, obj.expanded);
    if (!equals(obj.apiCalls, ArrayList_init_0()) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 4))
      output.encodeSerializableElement_blecud$(this.descriptor, 4, new ArrayListSerializer(ApiCall$$serializer_getInstance()), obj.apiCalls);
    if (!equals(obj.errorMessage, null) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 5))
      output.encodeNullableSerializableElement_orpvvi$(this.descriptor, 5, internal.StringSerializer, obj.errorMessage);
    output.endStructure_qatsm0$(this.descriptor);
  };
  Kit$$serializer.prototype.deserialize_nts5qn$ = function (decoder) {
    var index, readAll = false;
    var bitMask0 = 0;
    var local0
    , local1
    , local2
    , local3
    , local4
    , local5;
    var input = decoder.beginStructure_r0sa6z$(this.descriptor, []);
    loopLabel: while (true) {
      index = input.decodeElementIndex_qatsm0$(this.descriptor);
      switch (index) {
        case -2:
          readAll = true;
        case 0:
          local0 = input.decodeIntElement_3zr2iy$(this.descriptor, 0);
          bitMask0 |= 1;
          if (!readAll)
            break;
        case 1:
          local1 = input.decodeStringElement_3zr2iy$(this.descriptor, 1);
          bitMask0 |= 2;
          if (!readAll)
            break;
        case 2:
          local2 = (bitMask0 & 4) === 0 ? input.decodeSerializableElement_s44l7r$(this.descriptor, 2, new EnumSerializer(getKClass(Status))) : input.updateSerializableElement_ehubvl$(this.descriptor, 2, new EnumSerializer(getKClass(Status)), local2);
          bitMask0 |= 4;
          if (!readAll)
            break;
        case 3:
          local3 = input.decodeBooleanElement_3zr2iy$(this.descriptor, 3);
          bitMask0 |= 8;
          if (!readAll)
            break;
        case 4:
          local4 = (bitMask0 & 16) === 0 ? input.decodeSerializableElement_s44l7r$(this.descriptor, 4, new ArrayListSerializer(ApiCall$$serializer_getInstance())) : input.updateSerializableElement_ehubvl$(this.descriptor, 4, new ArrayListSerializer(ApiCall$$serializer_getInstance()), local4);
          bitMask0 |= 16;
          if (!readAll)
            break;
        case 5:
          local5 = (bitMask0 & 32) === 0 ? input.decodeNullableSerializableElement_cwlm4k$(this.descriptor, 5, internal.StringSerializer) : input.updateNullableSerializableElement_u33s02$(this.descriptor, 5, internal.StringSerializer, local5);
          bitMask0 |= 32;
          if (!readAll)
            break;
        case -1:
          break loopLabel;
        default:throw new UnknownFieldException(index);
      }
    }
    input.endStructure_qatsm0$(this.descriptor);
    return Kit_init(bitMask0, local0, local1, local2, local3, local4, local5, null);
  };
  Kit$$serializer.prototype.childSerializers = function () {
    return [internal.IntSerializer, internal.StringSerializer, new EnumSerializer(getKClass(Status)), internal.BooleanSerializer, new ArrayListSerializer(ApiCall$$serializer_getInstance()), new NullableSerializer(internal.StringSerializer)];
  };
  Kit$$serializer.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: '$serializer',
    interfaces: [GeneratedSerializer]
  };
  var Kit$$serializer_instance = null;
  function Kit$$serializer_getInstance() {
    if (Kit$$serializer_instance === null) {
      new Kit$$serializer();
    }
    return Kit$$serializer_instance;
  }
  function Kit_init(seen1, kitId, name, status, expanded, apiCalls, errorMessage, serializationConstructorMarker) {
    var $this = serializationConstructorMarker || Object.create(Kit.prototype);
    Event.call($this);
    if ((seen1 & 1) === 0)
      throw new MissingFieldException('kitId');
    else
      $this.kitId = kitId;
    if ((seen1 & 2) === 0)
      throw new MissingFieldException('name');
    else
      $this.name_19tu2l$_0 = name;
    if ((seen1 & 4) === 0)
      throw new MissingFieldException('status');
    else
      $this.status = status;
    if ((seen1 & 8) === 0)
      $this.expanded = false;
    else
      $this.expanded = expanded;
    if ((seen1 & 16) === 0)
      $this.apiCalls = ArrayList_init_0();
    else
      $this.apiCalls = apiCalls;
    if ((seen1 & 32) === 0)
      $this.errorMessage = null;
    else
      $this.errorMessage = errorMessage;
    return $this;
  }
  Kit.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Kit',
    interfaces: [Event]
  };
  Kit.prototype.component1 = function () {
    return this.kitId;
  };
  Kit.prototype.component2 = function () {
    return this.name;
  };
  Kit.prototype.component3 = function () {
    return this.status;
  };
  Kit.prototype.component4 = function () {
    return this.expanded;
  };
  Kit.prototype.component5 = function () {
    return this.apiCalls;
  };
  Kit.prototype.component6 = function () {
    return this.errorMessage;
  };
  Kit.prototype.copy_i4zjbc$ = function (kitId, name, status, expanded, apiCalls, errorMessage) {
    return new Kit(kitId === void 0 ? this.kitId : kitId, name === void 0 ? this.name : name, status === void 0 ? this.status : status, expanded === void 0 ? this.expanded : expanded, apiCalls === void 0 ? this.apiCalls : apiCalls, errorMessage === void 0 ? this.errorMessage : errorMessage);
  };
  Kit.prototype.toString = function () {
    return 'Kit(kitId=' + Kotlin.toString(this.kitId) + (', name=' + Kotlin.toString(this.name)) + (', status=' + Kotlin.toString(this.status)) + (', expanded=' + Kotlin.toString(this.expanded)) + (', apiCalls=' + Kotlin.toString(this.apiCalls)) + (', errorMessage=' + Kotlin.toString(this.errorMessage)) + ')';
  };
  Kit.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.kitId) | 0;
    result = result * 31 + Kotlin.hashCode(this.name) | 0;
    result = result * 31 + Kotlin.hashCode(this.status) | 0;
    result = result * 31 + Kotlin.hashCode(this.expanded) | 0;
    result = result * 31 + Kotlin.hashCode(this.apiCalls) | 0;
    result = result * 31 + Kotlin.hashCode(this.errorMessage) | 0;
    return result;
  };
  Kit.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.kitId, other.kitId) && Kotlin.equals(this.name, other.name) && Kotlin.equals(this.status, other.status) && Kotlin.equals(this.expanded, other.expanded) && Kotlin.equals(this.apiCalls, other.apiCalls) && Kotlin.equals(this.errorMessage, other.errorMessage)))));
  };
  function MessageTable(name, messages, bodyExpanded) {
    MessageTable$Companion_getInstance();
    if (messages === void 0)
      messages = HashMap_init();
    if (bodyExpanded === void 0)
      bodyExpanded = false;
    Event.call(this);
    this.name_2795lw$_0 = name;
    this.messages = messages;
    this.bodyExpanded = bodyExpanded;
  }
  Object.defineProperty(MessageTable.prototype, 'name', {
    get: function () {
      return this.name_2795lw$_0;
    }
  });
  function MessageTable$Companion() {
    MessageTable$Companion_instance = this;
  }
  MessageTable$Companion.prototype.serializer = function () {
    return MessageTable$$serializer_getInstance();
  };
  MessageTable$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var MessageTable$Companion_instance = null;
  function MessageTable$Companion_getInstance() {
    if (MessageTable$Companion_instance === null) {
      new MessageTable$Companion();
    }
    return MessageTable$Companion_instance;
  }
  function MessageTable$$serializer() {
    this.descriptor_48pcw0$_0 = new SerialClassDescImpl('com.mparticle.shared.events.MessageTable', this);
    this.descriptor.addElement_ivxn3r$('name', false);
    this.descriptor.addElement_ivxn3r$('messages', true);
    this.descriptor.addElement_ivxn3r$('bodyExpanded', true);
    MessageTable$$serializer_instance = this;
  }
  Object.defineProperty(MessageTable$$serializer.prototype, 'descriptor', {
    get: function () {
      return this.descriptor_48pcw0$_0;
    }
  });
  MessageTable$$serializer.prototype.serialize_awe97i$ = function (encoder, obj) {
    var output = encoder.beginStructure_r0sa6z$(this.descriptor, []);
    output.encodeStringElement_bgm7zs$(this.descriptor, 0, obj.name);
    if (!equals(obj.messages, HashMap_init()) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 1))
      output.encodeSerializableElement_blecud$(this.descriptor, 1, new LinkedHashMapSerializer(MessageEvent$$serializer_getInstance(), Mutable$Mutable$$serializer_init(internal.BooleanSerializer)), obj.messages);
    if (!equals(obj.bodyExpanded, false) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 2))
      output.encodeBooleanElement_w1b0nl$(this.descriptor, 2, obj.bodyExpanded);
    output.endStructure_qatsm0$(this.descriptor);
  };
  MessageTable$$serializer.prototype.deserialize_nts5qn$ = function (decoder) {
    var index, readAll = false;
    var bitMask0 = 0;
    var local0
    , local1
    , local2;
    var input = decoder.beginStructure_r0sa6z$(this.descriptor, []);
    loopLabel: while (true) {
      index = input.decodeElementIndex_qatsm0$(this.descriptor);
      switch (index) {
        case -2:
          readAll = true;
        case 0:
          local0 = input.decodeStringElement_3zr2iy$(this.descriptor, 0);
          bitMask0 |= 1;
          if (!readAll)
            break;
        case 1:
          local1 = (bitMask0 & 2) === 0 ? input.decodeSerializableElement_s44l7r$(this.descriptor, 1, new LinkedHashMapSerializer(MessageEvent$$serializer_getInstance(), Mutable$Mutable$$serializer_init(internal.BooleanSerializer))) : input.updateSerializableElement_ehubvl$(this.descriptor, 1, new LinkedHashMapSerializer(MessageEvent$$serializer_getInstance(), Mutable$Mutable$$serializer_init(internal.BooleanSerializer)), local1);
          bitMask0 |= 2;
          if (!readAll)
            break;
        case 2:
          local2 = input.decodeBooleanElement_3zr2iy$(this.descriptor, 2);
          bitMask0 |= 4;
          if (!readAll)
            break;
        case -1:
          break loopLabel;
        default:throw new UnknownFieldException(index);
      }
    }
    input.endStructure_qatsm0$(this.descriptor);
    return MessageTable_init(bitMask0, local0, local1, local2, null);
  };
  MessageTable$$serializer.prototype.childSerializers = function () {
    return [internal.StringSerializer, new LinkedHashMapSerializer(MessageEvent$$serializer_getInstance(), Mutable$Mutable$$serializer_init(internal.BooleanSerializer)), internal.BooleanSerializer];
  };
  MessageTable$$serializer.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: '$serializer',
    interfaces: [GeneratedSerializer]
  };
  var MessageTable$$serializer_instance = null;
  function MessageTable$$serializer_getInstance() {
    if (MessageTable$$serializer_instance === null) {
      new MessageTable$$serializer();
    }
    return MessageTable$$serializer_instance;
  }
  function MessageTable_init(seen1, name, messages, bodyExpanded, serializationConstructorMarker) {
    var $this = serializationConstructorMarker || Object.create(MessageTable.prototype);
    Event.call($this);
    if ((seen1 & 1) === 0)
      throw new MissingFieldException('name');
    else
      $this.name_2795lw$_0 = name;
    if ((seen1 & 2) === 0)
      $this.messages = HashMap_init();
    else
      $this.messages = messages;
    if ((seen1 & 4) === 0)
      $this.bodyExpanded = false;
    else
      $this.bodyExpanded = bodyExpanded;
    return $this;
  }
  MessageTable.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'MessageTable',
    interfaces: [Event]
  };
  function MessageEvent(tableName, body, status, storedTime, deleteTime, bodyExpanded, rowId, id) {
    MessageEvent$Companion_getInstance();
    if (storedTime === void 0)
      storedTime = (new PlatformApis()).getTimestamp();
    if (deleteTime === void 0)
      deleteTime = null;
    if (bodyExpanded === void 0)
      bodyExpanded = false;
    ChainableEvent.call(this);
    this.tableName_0 = tableName;
    this.body = body;
    this.status = status;
    this.storedTime = storedTime;
    this.deleteTime = deleteTime;
    this.bodyExpanded = bodyExpanded;
    this.rowId = rowId;
    this.id_f3046g$_0 = id;
    this.name_4se6s8$_0 = this.tableName_0;
  }
  Object.defineProperty(MessageEvent.prototype, 'id', {
    get: function () {
      return this.id_f3046g$_0;
    },
    set: function (id) {
      this.id_f3046g$_0 = id;
    }
  });
  Object.defineProperty(MessageEvent.prototype, 'name', {
    get: function () {
      return this.name_4se6s8$_0;
    }
  });
  function MessageEvent$Companion() {
    MessageEvent$Companion_instance = this;
  }
  MessageEvent$Companion.prototype.serializer = function () {
    return MessageEvent$$serializer_getInstance();
  };
  MessageEvent$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var MessageEvent$Companion_instance = null;
  function MessageEvent$Companion_getInstance() {
    if (MessageEvent$Companion_instance === null) {
      new MessageEvent$Companion();
    }
    return MessageEvent$Companion_instance;
  }
  function MessageEvent$$serializer() {
    this.descriptor_cf6jlg$_0 = new SerialClassDescImpl('com.mparticle.shared.events.MessageEvent', this);
    this.descriptor.addElement_ivxn3r$('tableName', false);
    this.descriptor.addElement_ivxn3r$('body', false);
    this.descriptor.addElement_ivxn3r$('status', false);
    this.descriptor.addElement_ivxn3r$('storedTime', true);
    this.descriptor.addElement_ivxn3r$('deleteTime', true);
    this.descriptor.addElement_ivxn3r$('bodyExpanded', true);
    this.descriptor.addElement_ivxn3r$('rowId', false);
    this.descriptor.addElement_ivxn3r$('message_id', false);
    this.descriptor.addElement_ivxn3r$('name', true);
    MessageEvent$$serializer_instance = this;
  }
  Object.defineProperty(MessageEvent$$serializer.prototype, 'descriptor', {
    get: function () {
      return this.descriptor_cf6jlg$_0;
    }
  });
  MessageEvent$$serializer.prototype.serialize_awe97i$ = function (encoder, obj) {
    var output = encoder.beginStructure_r0sa6z$(this.descriptor, []);
    output.encodeStringElement_bgm7zs$(this.descriptor, 0, obj.tableName_0);
    output.encodeStringElement_bgm7zs$(this.descriptor, 1, obj.body);
    output.encodeSerializableElement_blecud$(this.descriptor, 2, new EnumSerializer(getKClass(Status)), obj.status);
    if (!equals(obj.storedTime, (new PlatformApis()).getTimestamp()) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 3))
      output.encodeLongElement_a3zgoj$(this.descriptor, 3, obj.storedTime);
    if (!equals(obj.deleteTime, null) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 4))
      output.encodeNullableSerializableElement_orpvvi$(this.descriptor, 4, internal.LongSerializer, obj.deleteTime);
    if (!equals(obj.bodyExpanded, false) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 5))
      output.encodeBooleanElement_w1b0nl$(this.descriptor, 5, obj.bodyExpanded);
    output.encodeLongElement_a3zgoj$(this.descriptor, 6, obj.rowId);
    output.encodeIntElement_4wpqag$(this.descriptor, 7, obj.id);
    if (!equals(obj.name, this.tableName_0) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 8))
      output.encodeStringElement_bgm7zs$(this.descriptor, 8, obj.name);
    output.endStructure_qatsm0$(this.descriptor);
  };
  MessageEvent$$serializer.prototype.deserialize_nts5qn$ = function (decoder) {
    var index, readAll = false;
    var bitMask0 = 0;
    var local0
    , local1
    , local2
    , local3
    , local4
    , local5
    , local6
    , local7
    , local8;
    var input = decoder.beginStructure_r0sa6z$(this.descriptor, []);
    loopLabel: while (true) {
      index = input.decodeElementIndex_qatsm0$(this.descriptor);
      switch (index) {
        case -2:
          readAll = true;
        case 0:
          local0 = input.decodeStringElement_3zr2iy$(this.descriptor, 0);
          bitMask0 |= 1;
          if (!readAll)
            break;
        case 1:
          local1 = input.decodeStringElement_3zr2iy$(this.descriptor, 1);
          bitMask0 |= 2;
          if (!readAll)
            break;
        case 2:
          local2 = (bitMask0 & 4) === 0 ? input.decodeSerializableElement_s44l7r$(this.descriptor, 2, new EnumSerializer(getKClass(Status))) : input.updateSerializableElement_ehubvl$(this.descriptor, 2, new EnumSerializer(getKClass(Status)), local2);
          bitMask0 |= 4;
          if (!readAll)
            break;
        case 3:
          local3 = input.decodeLongElement_3zr2iy$(this.descriptor, 3);
          bitMask0 |= 8;
          if (!readAll)
            break;
        case 4:
          local4 = (bitMask0 & 16) === 0 ? input.decodeNullableSerializableElement_cwlm4k$(this.descriptor, 4, internal.LongSerializer) : input.updateNullableSerializableElement_u33s02$(this.descriptor, 4, internal.LongSerializer, local4);
          bitMask0 |= 16;
          if (!readAll)
            break;
        case 5:
          local5 = input.decodeBooleanElement_3zr2iy$(this.descriptor, 5);
          bitMask0 |= 32;
          if (!readAll)
            break;
        case 6:
          local6 = input.decodeLongElement_3zr2iy$(this.descriptor, 6);
          bitMask0 |= 64;
          if (!readAll)
            break;
        case 7:
          local7 = input.decodeIntElement_3zr2iy$(this.descriptor, 7);
          bitMask0 |= 128;
          if (!readAll)
            break;
        case 8:
          local8 = input.decodeStringElement_3zr2iy$(this.descriptor, 8);
          bitMask0 |= 256;
          if (!readAll)
            break;
        case -1:
          break loopLabel;
        default:throw new UnknownFieldException(index);
      }
    }
    input.endStructure_qatsm0$(this.descriptor);
    return MessageEvent_init(bitMask0, local0, local1, local2, local3, local4, local5, local6, local7, local8, null);
  };
  MessageEvent$$serializer.prototype.childSerializers = function () {
    return [internal.StringSerializer, internal.StringSerializer, new EnumSerializer(getKClass(Status)), internal.LongSerializer, new NullableSerializer(internal.LongSerializer), internal.BooleanSerializer, internal.LongSerializer, internal.IntSerializer, internal.StringSerializer];
  };
  MessageEvent$$serializer.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: '$serializer',
    interfaces: [GeneratedSerializer]
  };
  var MessageEvent$$serializer_instance = null;
  function MessageEvent$$serializer_getInstance() {
    if (MessageEvent$$serializer_instance === null) {
      new MessageEvent$$serializer();
    }
    return MessageEvent$$serializer_instance;
  }
  function MessageEvent_init(seen1, tableName, body, status, storedTime, deleteTime, bodyExpanded, rowId, id, name, serializationConstructorMarker) {
    var $this = serializationConstructorMarker || Object.create(MessageEvent.prototype);
    ChainableEvent.call($this);
    if ((seen1 & 1) === 0)
      throw new MissingFieldException('tableName');
    else
      $this.tableName_0 = tableName;
    if ((seen1 & 2) === 0)
      throw new MissingFieldException('body');
    else
      $this.body = body;
    if ((seen1 & 4) === 0)
      throw new MissingFieldException('status');
    else
      $this.status = status;
    if ((seen1 & 8) === 0)
      $this.storedTime = (new PlatformApis()).getTimestamp();
    else
      $this.storedTime = storedTime;
    if ((seen1 & 16) === 0)
      $this.deleteTime = null;
    else
      $this.deleteTime = deleteTime;
    if ((seen1 & 32) === 0)
      $this.bodyExpanded = false;
    else
      $this.bodyExpanded = bodyExpanded;
    if ((seen1 & 64) === 0)
      throw new MissingFieldException('rowId');
    else
      $this.rowId = rowId;
    if ((seen1 & 128) === 0)
      throw new MissingFieldException('message_id');
    else
      $this.id_f3046g$_0 = id;
    if ((seen1 & 256) === 0)
      $this.name_4se6s8$_0 = $this.tableName_0;
    else
      $this.name_4se6s8$_0 = name;
    return $this;
  }
  MessageEvent.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'MessageEvent',
    interfaces: [ChainableEvent]
  };
  function NetworkRequest(title, status, url, body, timeSent, bodyExpanded, responseExpanded, expanded, responseCode, responseBody, id) {
    NetworkRequest$Companion_getInstance();
    if (bodyExpanded === void 0)
      bodyExpanded = false;
    if (responseExpanded === void 0)
      responseExpanded = false;
    if (expanded === void 0)
      expanded = false;
    if (responseCode === void 0)
      responseCode = '-';
    if (responseBody === void 0)
      responseBody = null;
    ChainableEvent.call(this);
    this.title = title;
    this.status = status;
    this.url = url;
    this.body = body;
    this.timeSent = timeSent;
    this.bodyExpanded = bodyExpanded;
    this.responseExpanded = responseExpanded;
    this.expanded = expanded;
    this.responseCode = responseCode;
    this.responseBody = responseBody;
    this.id_i9iqva$_0 = id;
    this.name_7lbvbq$_0 = this.title;
  }
  Object.defineProperty(NetworkRequest.prototype, 'id', {
    get: function () {
      return this.id_i9iqva$_0;
    },
    set: function (id) {
      this.id_i9iqva$_0 = id;
    }
  });
  Object.defineProperty(NetworkRequest.prototype, 'name', {
    get: function () {
      return this.name_7lbvbq$_0;
    }
  });
  NetworkRequest.prototype.copy = function () {
    return new NetworkRequest(this.name, this.status, this.url, this.body, this.timeSent, this.bodyExpanded, this.responseExpanded, this.expanded, this.responseCode, this.responseBody, this.id);
  };
  function NetworkRequest$Companion() {
    NetworkRequest$Companion_instance = this;
  }
  NetworkRequest$Companion.prototype.serializer = function () {
    return NetworkRequest$$serializer_getInstance();
  };
  NetworkRequest$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var NetworkRequest$Companion_instance = null;
  function NetworkRequest$Companion_getInstance() {
    if (NetworkRequest$Companion_instance === null) {
      new NetworkRequest$Companion();
    }
    return NetworkRequest$Companion_instance;
  }
  function NetworkRequest$$serializer() {
    this.descriptor_fbbh8a$_0 = new SerialClassDescImpl('com.mparticle.shared.events.NetworkRequest', this);
    this.descriptor.addElement_ivxn3r$('title', false);
    this.descriptor.addElement_ivxn3r$('status', false);
    this.descriptor.addElement_ivxn3r$('url', false);
    this.descriptor.addElement_ivxn3r$('body', false);
    this.descriptor.addElement_ivxn3r$('timeSent', false);
    this.descriptor.addElement_ivxn3r$('bodyExpanded', true);
    this.descriptor.addElement_ivxn3r$('responseExpanded', true);
    this.descriptor.addElement_ivxn3r$('expanded', true);
    this.descriptor.addElement_ivxn3r$('responseCode', true);
    this.descriptor.addElement_ivxn3r$('responseBody', true);
    this.descriptor.addElement_ivxn3r$('network_id', false);
    this.descriptor.addElement_ivxn3r$('name', true);
    NetworkRequest$$serializer_instance = this;
  }
  Object.defineProperty(NetworkRequest$$serializer.prototype, 'descriptor', {
    get: function () {
      return this.descriptor_fbbh8a$_0;
    }
  });
  NetworkRequest$$serializer.prototype.serialize_awe97i$ = function (encoder, obj) {
    var output = encoder.beginStructure_r0sa6z$(this.descriptor, []);
    output.encodeStringElement_bgm7zs$(this.descriptor, 0, obj.title);
    output.encodeSerializableElement_blecud$(this.descriptor, 1, new EnumSerializer(getKClass(Status)), obj.status);
    output.encodeStringElement_bgm7zs$(this.descriptor, 2, obj.url);
    output.encodeStringElement_bgm7zs$(this.descriptor, 3, obj.body);
    output.encodeLongElement_a3zgoj$(this.descriptor, 4, obj.timeSent);
    if (!equals(obj.bodyExpanded, false) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 5))
      output.encodeBooleanElement_w1b0nl$(this.descriptor, 5, obj.bodyExpanded);
    if (!equals(obj.responseExpanded, false) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 6))
      output.encodeBooleanElement_w1b0nl$(this.descriptor, 6, obj.responseExpanded);
    if (!equals(obj.expanded, false) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 7))
      output.encodeBooleanElement_w1b0nl$(this.descriptor, 7, obj.expanded);
    if (!equals(obj.responseCode, '-') || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 8))
      output.encodeStringElement_bgm7zs$(this.descriptor, 8, obj.responseCode);
    if (!equals(obj.responseBody, null) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 9))
      output.encodeNullableSerializableElement_orpvvi$(this.descriptor, 9, internal.StringSerializer, obj.responseBody);
    output.encodeIntElement_4wpqag$(this.descriptor, 10, obj.id);
    if (!equals(obj.name, this.title) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 11))
      output.encodeStringElement_bgm7zs$(this.descriptor, 11, obj.name);
    output.endStructure_qatsm0$(this.descriptor);
  };
  NetworkRequest$$serializer.prototype.deserialize_nts5qn$ = function (decoder) {
    var index, readAll = false;
    var bitMask0 = 0;
    var local0
    , local1
    , local2
    , local3
    , local4
    , local5
    , local6
    , local7
    , local8
    , local9
    , local10
    , local11;
    var input = decoder.beginStructure_r0sa6z$(this.descriptor, []);
    loopLabel: while (true) {
      index = input.decodeElementIndex_qatsm0$(this.descriptor);
      switch (index) {
        case -2:
          readAll = true;
        case 0:
          local0 = input.decodeStringElement_3zr2iy$(this.descriptor, 0);
          bitMask0 |= 1;
          if (!readAll)
            break;
        case 1:
          local1 = (bitMask0 & 2) === 0 ? input.decodeSerializableElement_s44l7r$(this.descriptor, 1, new EnumSerializer(getKClass(Status))) : input.updateSerializableElement_ehubvl$(this.descriptor, 1, new EnumSerializer(getKClass(Status)), local1);
          bitMask0 |= 2;
          if (!readAll)
            break;
        case 2:
          local2 = input.decodeStringElement_3zr2iy$(this.descriptor, 2);
          bitMask0 |= 4;
          if (!readAll)
            break;
        case 3:
          local3 = input.decodeStringElement_3zr2iy$(this.descriptor, 3);
          bitMask0 |= 8;
          if (!readAll)
            break;
        case 4:
          local4 = input.decodeLongElement_3zr2iy$(this.descriptor, 4);
          bitMask0 |= 16;
          if (!readAll)
            break;
        case 5:
          local5 = input.decodeBooleanElement_3zr2iy$(this.descriptor, 5);
          bitMask0 |= 32;
          if (!readAll)
            break;
        case 6:
          local6 = input.decodeBooleanElement_3zr2iy$(this.descriptor, 6);
          bitMask0 |= 64;
          if (!readAll)
            break;
        case 7:
          local7 = input.decodeBooleanElement_3zr2iy$(this.descriptor, 7);
          bitMask0 |= 128;
          if (!readAll)
            break;
        case 8:
          local8 = input.decodeStringElement_3zr2iy$(this.descriptor, 8);
          bitMask0 |= 256;
          if (!readAll)
            break;
        case 9:
          local9 = (bitMask0 & 512) === 0 ? input.decodeNullableSerializableElement_cwlm4k$(this.descriptor, 9, internal.StringSerializer) : input.updateNullableSerializableElement_u33s02$(this.descriptor, 9, internal.StringSerializer, local9);
          bitMask0 |= 512;
          if (!readAll)
            break;
        case 10:
          local10 = input.decodeIntElement_3zr2iy$(this.descriptor, 10);
          bitMask0 |= 1024;
          if (!readAll)
            break;
        case 11:
          local11 = input.decodeStringElement_3zr2iy$(this.descriptor, 11);
          bitMask0 |= 2048;
          if (!readAll)
            break;
        case -1:
          break loopLabel;
        default:throw new UnknownFieldException(index);
      }
    }
    input.endStructure_qatsm0$(this.descriptor);
    return NetworkRequest_init(bitMask0, local0, local1, local2, local3, local4, local5, local6, local7, local8, local9, local10, local11, null);
  };
  NetworkRequest$$serializer.prototype.childSerializers = function () {
    return [internal.StringSerializer, new EnumSerializer(getKClass(Status)), internal.StringSerializer, internal.StringSerializer, internal.LongSerializer, internal.BooleanSerializer, internal.BooleanSerializer, internal.BooleanSerializer, internal.StringSerializer, new NullableSerializer(internal.StringSerializer), internal.IntSerializer, internal.StringSerializer];
  };
  NetworkRequest$$serializer.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: '$serializer',
    interfaces: [GeneratedSerializer]
  };
  var NetworkRequest$$serializer_instance = null;
  function NetworkRequest$$serializer_getInstance() {
    if (NetworkRequest$$serializer_instance === null) {
      new NetworkRequest$$serializer();
    }
    return NetworkRequest$$serializer_instance;
  }
  function NetworkRequest_init(seen1, title, status, url, body, timeSent, bodyExpanded, responseExpanded, expanded, responseCode, responseBody, id, name, serializationConstructorMarker) {
    var $this = serializationConstructorMarker || Object.create(NetworkRequest.prototype);
    ChainableEvent.call($this);
    if ((seen1 & 1) === 0)
      throw new MissingFieldException('title');
    else
      $this.title = title;
    if ((seen1 & 2) === 0)
      throw new MissingFieldException('status');
    else
      $this.status = status;
    if ((seen1 & 4) === 0)
      throw new MissingFieldException('url');
    else
      $this.url = url;
    if ((seen1 & 8) === 0)
      throw new MissingFieldException('body');
    else
      $this.body = body;
    if ((seen1 & 16) === 0)
      throw new MissingFieldException('timeSent');
    else
      $this.timeSent = timeSent;
    if ((seen1 & 32) === 0)
      $this.bodyExpanded = false;
    else
      $this.bodyExpanded = bodyExpanded;
    if ((seen1 & 64) === 0)
      $this.responseExpanded = false;
    else
      $this.responseExpanded = responseExpanded;
    if ((seen1 & 128) === 0)
      $this.expanded = false;
    else
      $this.expanded = expanded;
    if ((seen1 & 256) === 0)
      $this.responseCode = '-';
    else
      $this.responseCode = responseCode;
    if ((seen1 & 512) === 0)
      $this.responseBody = null;
    else
      $this.responseBody = responseBody;
    if ((seen1 & 1024) === 0)
      throw new MissingFieldException('network_id');
    else
      $this.id_i9iqva$_0 = id;
    if ((seen1 & 2048) === 0)
      $this.name_7lbvbq$_0 = $this.title;
    else
      $this.name_7lbvbq$_0 = name;
    return $this;
  }
  NetworkRequest.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'NetworkRequest',
    interfaces: [ChainableEvent]
  };
  function StateEvent(name, priority, expanded) {
    StateEvent$Companion_getInstance();
    if (priority === void 0)
      priority = 0;
    if (expanded === void 0)
      expanded = false;
    Event.call(this);
    this.name_5x3ixu$_0 = name;
    this.priority = priority;
    this.expanded = expanded;
  }
  Object.defineProperty(StateEvent.prototype, 'name', {
    get: function () {
      return this.name_5x3ixu$_0;
    },
    set: function (name) {
      this.name_5x3ixu$_0 = name;
    }
  });
  function StateEvent$Companion() {
    StateEvent$Companion_instance = this;
  }
  StateEvent$Companion.prototype.serializer = function () {
    return StateEvent$$serializer_getInstance();
  };
  StateEvent$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var StateEvent$Companion_instance = null;
  function StateEvent$Companion_getInstance() {
    if (StateEvent$Companion_instance === null) {
      new StateEvent$Companion();
    }
    return StateEvent$Companion_instance;
  }
  function StateEvent$$serializer() {
    this.descriptor_o7gyri$_0 = new SerialClassDescImpl('com.mparticle.shared.events.StateEvent', this);
    this.descriptor.addElement_ivxn3r$('name', false);
    this.descriptor.addElement_ivxn3r$('priority', true);
    this.descriptor.addElement_ivxn3r$('expanded', true);
    StateEvent$$serializer_instance = this;
  }
  Object.defineProperty(StateEvent$$serializer.prototype, 'descriptor', {
    get: function () {
      return this.descriptor_o7gyri$_0;
    }
  });
  StateEvent$$serializer.prototype.serialize_awe97i$ = function (encoder, obj) {
    var output = encoder.beginStructure_r0sa6z$(this.descriptor, []);
    output.encodeStringElement_bgm7zs$(this.descriptor, 0, obj.name);
    if (!equals(obj.priority, 0) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 1))
      output.encodeIntElement_4wpqag$(this.descriptor, 1, obj.priority);
    if (!equals(obj.expanded, false) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 2))
      output.encodeBooleanElement_w1b0nl$(this.descriptor, 2, obj.expanded);
    output.endStructure_qatsm0$(this.descriptor);
  };
  StateEvent$$serializer.prototype.deserialize_nts5qn$ = function (decoder) {
    var index, readAll = false;
    var bitMask0 = 0;
    var local0
    , local1
    , local2;
    var input = decoder.beginStructure_r0sa6z$(this.descriptor, []);
    loopLabel: while (true) {
      index = input.decodeElementIndex_qatsm0$(this.descriptor);
      switch (index) {
        case -2:
          readAll = true;
        case 0:
          local0 = input.decodeStringElement_3zr2iy$(this.descriptor, 0);
          bitMask0 |= 1;
          if (!readAll)
            break;
        case 1:
          local1 = input.decodeIntElement_3zr2iy$(this.descriptor, 1);
          bitMask0 |= 2;
          if (!readAll)
            break;
        case 2:
          local2 = input.decodeBooleanElement_3zr2iy$(this.descriptor, 2);
          bitMask0 |= 4;
          if (!readAll)
            break;
        case -1:
          break loopLabel;
        default:throw new UnknownFieldException(index);
      }
    }
    input.endStructure_qatsm0$(this.descriptor);
    return StateEvent_init(bitMask0, local0, local1, local2, null);
  };
  StateEvent$$serializer.prototype.childSerializers = function () {
    return [internal.StringSerializer, internal.IntSerializer, internal.BooleanSerializer];
  };
  StateEvent$$serializer.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: '$serializer',
    interfaces: [GeneratedSerializer]
  };
  var StateEvent$$serializer_instance = null;
  function StateEvent$$serializer_getInstance() {
    if (StateEvent$$serializer_instance === null) {
      new StateEvent$$serializer();
    }
    return StateEvent$$serializer_instance;
  }
  function StateEvent_init(seen1, name, priority, expanded, serializationConstructorMarker) {
    var $this = serializationConstructorMarker || Object.create(StateEvent.prototype);
    Event.call($this);
    if ((seen1 & 1) === 0)
      throw new MissingFieldException('name');
    else
      $this.name_5x3ixu$_0 = name;
    if ((seen1 & 2) === 0)
      $this.priority = 0;
    else
      $this.priority = priority;
    if ((seen1 & 4) === 0)
      $this.expanded = false;
    else
      $this.expanded = expanded;
    return $this;
  }
  StateEvent.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'StateEvent',
    interfaces: [Event]
  };
  function StateCurrentUser(user, attributesExpanded, identitiesExpanded) {
    StateCurrentUser$Companion_getInstance();
    if (attributesExpanded === void 0)
      attributesExpanded = false;
    if (identitiesExpanded === void 0)
      identitiesExpanded = false;
    StateEvent.call(this, 'Current User');
    this.user = user;
    this.attributesExpanded = attributesExpanded;
    this.identitiesExpanded = identitiesExpanded;
  }
  function StateCurrentUser$Companion() {
    StateCurrentUser$Companion_instance = this;
  }
  StateCurrentUser$Companion.prototype.serializer = function () {
    return StateCurrentUser$$serializer_getInstance();
  };
  StateCurrentUser$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var StateCurrentUser$Companion_instance = null;
  function StateCurrentUser$Companion_getInstance() {
    if (StateCurrentUser$Companion_instance === null) {
      new StateCurrentUser$Companion();
    }
    return StateCurrentUser$Companion_instance;
  }
  function StateCurrentUser$$serializer() {
    this.descriptor_r8y970$_0 = new SerialClassDescImpl('com.mparticle.shared.events.StateCurrentUser', this);
    this.descriptor.addElement_ivxn3r$('name', false);
    this.descriptor.addElement_ivxn3r$('priority', true);
    this.descriptor.addElement_ivxn3r$('expanded', true);
    this.descriptor.addElement_ivxn3r$('user', false);
    this.descriptor.addElement_ivxn3r$('attributesExpanded', true);
    this.descriptor.addElement_ivxn3r$('identitiesExpanded', true);
    StateCurrentUser$$serializer_instance = this;
  }
  Object.defineProperty(StateCurrentUser$$serializer.prototype, 'descriptor', {
    get: function () {
      return this.descriptor_r8y970$_0;
    }
  });
  StateCurrentUser$$serializer.prototype.serialize_awe97i$ = function (encoder, obj) {
    var output = encoder.beginStructure_r0sa6z$(this.descriptor, []);
    output.encodeStringElement_bgm7zs$(this.descriptor, 0, obj.name);
    if (!equals(obj.priority, 0) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 1))
      output.encodeIntElement_4wpqag$(this.descriptor, 1, obj.priority);
    if (!equals(obj.expanded, false) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 2))
      output.encodeBooleanElement_w1b0nl$(this.descriptor, 2, obj.expanded);
    output.encodeNullableSerializableElement_orpvvi$(this.descriptor, 3, User$$serializer_getInstance(), obj.user);
    if (!equals(obj.attributesExpanded, false) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 4))
      output.encodeBooleanElement_w1b0nl$(this.descriptor, 4, obj.attributesExpanded);
    if (!equals(obj.identitiesExpanded, false) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 5))
      output.encodeBooleanElement_w1b0nl$(this.descriptor, 5, obj.identitiesExpanded);
    output.endStructure_qatsm0$(this.descriptor);
  };
  StateCurrentUser$$serializer.prototype.deserialize_nts5qn$ = function (decoder) {
    var index, readAll = false;
    var bitMask0 = 0;
    var local0
    , local1
    , local2
    , local3
    , local4
    , local5;
    var input = decoder.beginStructure_r0sa6z$(this.descriptor, []);
    loopLabel: while (true) {
      index = input.decodeElementIndex_qatsm0$(this.descriptor);
      switch (index) {
        case -2:
          readAll = true;
        case 0:
          local0 = input.decodeStringElement_3zr2iy$(this.descriptor, 0);
          bitMask0 |= 1;
          if (!readAll)
            break;
        case 1:
          local1 = input.decodeIntElement_3zr2iy$(this.descriptor, 1);
          bitMask0 |= 2;
          if (!readAll)
            break;
        case 2:
          local2 = input.decodeBooleanElement_3zr2iy$(this.descriptor, 2);
          bitMask0 |= 4;
          if (!readAll)
            break;
        case 3:
          local3 = (bitMask0 & 8) === 0 ? input.decodeNullableSerializableElement_cwlm4k$(this.descriptor, 3, User$$serializer_getInstance()) : input.updateNullableSerializableElement_u33s02$(this.descriptor, 3, User$$serializer_getInstance(), local3);
          bitMask0 |= 8;
          if (!readAll)
            break;
        case 4:
          local4 = input.decodeBooleanElement_3zr2iy$(this.descriptor, 4);
          bitMask0 |= 16;
          if (!readAll)
            break;
        case 5:
          local5 = input.decodeBooleanElement_3zr2iy$(this.descriptor, 5);
          bitMask0 |= 32;
          if (!readAll)
            break;
        case -1:
          break loopLabel;
        default:throw new UnknownFieldException(index);
      }
    }
    input.endStructure_qatsm0$(this.descriptor);
    return StateCurrentUser_init(bitMask0, local0, local1, local2, local3, local4, local5, null);
  };
  StateCurrentUser$$serializer.prototype.childSerializers = function () {
    return [internal.StringSerializer, internal.IntSerializer, internal.BooleanSerializer, new NullableSerializer(User$$serializer_getInstance()), internal.BooleanSerializer, internal.BooleanSerializer];
  };
  StateCurrentUser$$serializer.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: '$serializer',
    interfaces: [GeneratedSerializer]
  };
  var StateCurrentUser$$serializer_instance = null;
  function StateCurrentUser$$serializer_getInstance() {
    if (StateCurrentUser$$serializer_instance === null) {
      new StateCurrentUser$$serializer();
    }
    return StateCurrentUser$$serializer_instance;
  }
  function StateCurrentUser_init(seen1, name, priority, expanded, user, attributesExpanded, identitiesExpanded, serializationConstructorMarker) {
    var $this = serializationConstructorMarker || Object.create(StateCurrentUser.prototype);
    $this = StateEvent_init(seen1, name, priority, expanded, $this);
    if ((seen1 & 8) === 0)
      throw new MissingFieldException('user');
    else
      $this.user = user;
    if ((seen1 & 16) === 0)
      $this.attributesExpanded = false;
    else
      $this.attributesExpanded = attributesExpanded;
    if ((seen1 & 32) === 0)
      $this.identitiesExpanded = false;
    else
      $this.identitiesExpanded = identitiesExpanded;
    return $this;
  }
  StateCurrentUser.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'StateCurrentUser',
    interfaces: [StateEvent]
  };
  StateCurrentUser.prototype.component1 = function () {
    return this.user;
  };
  StateCurrentUser.prototype.component2 = function () {
    return this.attributesExpanded;
  };
  StateCurrentUser.prototype.component3 = function () {
    return this.identitiesExpanded;
  };
  StateCurrentUser.prototype.copy_24cxdf$ = function (user, attributesExpanded, identitiesExpanded) {
    return new StateCurrentUser(user === void 0 ? this.user : user, attributesExpanded === void 0 ? this.attributesExpanded : attributesExpanded, identitiesExpanded === void 0 ? this.identitiesExpanded : identitiesExpanded);
  };
  StateCurrentUser.prototype.toString = function () {
    return 'StateCurrentUser(user=' + Kotlin.toString(this.user) + (', attributesExpanded=' + Kotlin.toString(this.attributesExpanded)) + (', identitiesExpanded=' + Kotlin.toString(this.identitiesExpanded)) + ')';
  };
  StateCurrentUser.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.user) | 0;
    result = result * 31 + Kotlin.hashCode(this.attributesExpanded) | 0;
    result = result * 31 + Kotlin.hashCode(this.identitiesExpanded) | 0;
    return result;
  };
  StateCurrentUser.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.user, other.user) && Kotlin.equals(this.attributesExpanded, other.attributesExpanded) && Kotlin.equals(this.identitiesExpanded, other.identitiesExpanded)))));
  };
  function StateAllUsers(users) {
    StateAllUsers$Companion_getInstance();
    StateEvent.call(this, 'Previous Users');
    this.users = users;
  }
  function StateAllUsers$Companion() {
    StateAllUsers$Companion_instance = this;
  }
  StateAllUsers$Companion.prototype.serializer = function () {
    return StateAllUsers$$serializer_getInstance();
  };
  StateAllUsers$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var StateAllUsers$Companion_instance = null;
  function StateAllUsers$Companion_getInstance() {
    if (StateAllUsers$Companion_instance === null) {
      new StateAllUsers$Companion();
    }
    return StateAllUsers$Companion_instance;
  }
  function StateAllUsers$$serializer() {
    this.descriptor_bukfxl$_0 = new SerialClassDescImpl('com.mparticle.shared.events.StateAllUsers', this);
    this.descriptor.addElement_ivxn3r$('name', false);
    this.descriptor.addElement_ivxn3r$('priority', true);
    this.descriptor.addElement_ivxn3r$('expanded', true);
    this.descriptor.addElement_ivxn3r$('users', false);
    StateAllUsers$$serializer_instance = this;
  }
  Object.defineProperty(StateAllUsers$$serializer.prototype, 'descriptor', {
    get: function () {
      return this.descriptor_bukfxl$_0;
    }
  });
  StateAllUsers$$serializer.prototype.serialize_awe97i$ = function (encoder, obj) {
    var output = encoder.beginStructure_r0sa6z$(this.descriptor, []);
    output.encodeStringElement_bgm7zs$(this.descriptor, 0, obj.name);
    if (!equals(obj.priority, 0) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 1))
      output.encodeIntElement_4wpqag$(this.descriptor, 1, obj.priority);
    if (!equals(obj.expanded, false) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 2))
      output.encodeBooleanElement_w1b0nl$(this.descriptor, 2, obj.expanded);
    output.encodeSerializableElement_blecud$(this.descriptor, 3, new LinkedHashMapSerializer(User$$serializer_getInstance(), Mutable$Mutable$$serializer_init(internal.BooleanSerializer)), obj.users);
    output.endStructure_qatsm0$(this.descriptor);
  };
  StateAllUsers$$serializer.prototype.deserialize_nts5qn$ = function (decoder) {
    var index, readAll = false;
    var bitMask0 = 0;
    var local0
    , local1
    , local2
    , local3;
    var input = decoder.beginStructure_r0sa6z$(this.descriptor, []);
    loopLabel: while (true) {
      index = input.decodeElementIndex_qatsm0$(this.descriptor);
      switch (index) {
        case -2:
          readAll = true;
        case 0:
          local0 = input.decodeStringElement_3zr2iy$(this.descriptor, 0);
          bitMask0 |= 1;
          if (!readAll)
            break;
        case 1:
          local1 = input.decodeIntElement_3zr2iy$(this.descriptor, 1);
          bitMask0 |= 2;
          if (!readAll)
            break;
        case 2:
          local2 = input.decodeBooleanElement_3zr2iy$(this.descriptor, 2);
          bitMask0 |= 4;
          if (!readAll)
            break;
        case 3:
          local3 = (bitMask0 & 8) === 0 ? input.decodeSerializableElement_s44l7r$(this.descriptor, 3, new LinkedHashMapSerializer(User$$serializer_getInstance(), Mutable$Mutable$$serializer_init(internal.BooleanSerializer))) : input.updateSerializableElement_ehubvl$(this.descriptor, 3, new LinkedHashMapSerializer(User$$serializer_getInstance(), Mutable$Mutable$$serializer_init(internal.BooleanSerializer)), local3);
          bitMask0 |= 8;
          if (!readAll)
            break;
        case -1:
          break loopLabel;
        default:throw new UnknownFieldException(index);
      }
    }
    input.endStructure_qatsm0$(this.descriptor);
    return StateAllUsers_init(bitMask0, local0, local1, local2, local3, null);
  };
  StateAllUsers$$serializer.prototype.childSerializers = function () {
    return [internal.StringSerializer, internal.IntSerializer, internal.BooleanSerializer, new LinkedHashMapSerializer(User$$serializer_getInstance(), Mutable$Mutable$$serializer_init(internal.BooleanSerializer))];
  };
  StateAllUsers$$serializer.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: '$serializer',
    interfaces: [GeneratedSerializer]
  };
  var StateAllUsers$$serializer_instance = null;
  function StateAllUsers$$serializer_getInstance() {
    if (StateAllUsers$$serializer_instance === null) {
      new StateAllUsers$$serializer();
    }
    return StateAllUsers$$serializer_instance;
  }
  function StateAllUsers_init(seen1, name, priority, expanded, users, serializationConstructorMarker) {
    var $this = serializationConstructorMarker || Object.create(StateAllUsers.prototype);
    $this = StateEvent_init(seen1, name, priority, expanded, $this);
    if ((seen1 & 8) === 0)
      throw new MissingFieldException('users');
    else
      $this.users = users;
    return $this;
  }
  StateAllUsers.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'StateAllUsers',
    interfaces: [StateEvent]
  };
  StateAllUsers.prototype.component1 = function () {
    return this.users;
  };
  StateAllUsers.prototype.copy_8cpaza$ = function (users) {
    return new StateAllUsers(users === void 0 ? this.users : users);
  };
  StateAllUsers.prototype.toString = function () {
    return 'StateAllUsers(users=' + Kotlin.toString(this.users) + ')';
  };
  StateAllUsers.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.users) | 0;
    return result;
  };
  StateAllUsers.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.users, other.users))));
  };
  function StateAllSessions(sessions) {
    StateAllSessions$Companion_getInstance();
    StateEvent.call(this, 'Previous Sessions');
    this.sessions = sessions;
  }
  function StateAllSessions$Companion() {
    StateAllSessions$Companion_instance = this;
  }
  StateAllSessions$Companion.prototype.serializer = function () {
    return StateAllSessions$$serializer_getInstance();
  };
  StateAllSessions$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var StateAllSessions$Companion_instance = null;
  function StateAllSessions$Companion_getInstance() {
    if (StateAllSessions$Companion_instance === null) {
      new StateAllSessions$Companion();
    }
    return StateAllSessions$Companion_instance;
  }
  function StateAllSessions$$serializer() {
    this.descriptor_o9igli$_0 = new SerialClassDescImpl('com.mparticle.shared.events.StateAllSessions', this);
    this.descriptor.addElement_ivxn3r$('name', false);
    this.descriptor.addElement_ivxn3r$('priority', true);
    this.descriptor.addElement_ivxn3r$('expanded', true);
    this.descriptor.addElement_ivxn3r$('sessions', false);
    StateAllSessions$$serializer_instance = this;
  }
  Object.defineProperty(StateAllSessions$$serializer.prototype, 'descriptor', {
    get: function () {
      return this.descriptor_o9igli$_0;
    }
  });
  StateAllSessions$$serializer.prototype.serialize_awe97i$ = function (encoder, obj) {
    var output = encoder.beginStructure_r0sa6z$(this.descriptor, []);
    output.encodeStringElement_bgm7zs$(this.descriptor, 0, obj.name);
    if (!equals(obj.priority, 0) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 1))
      output.encodeIntElement_4wpqag$(this.descriptor, 1, obj.priority);
    if (!equals(obj.expanded, false) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 2))
      output.encodeBooleanElement_w1b0nl$(this.descriptor, 2, obj.expanded);
    output.encodeSerializableElement_blecud$(this.descriptor, 3, new LinkedHashMapSerializer(StateStatus$$serializer_getInstance(), Mutable$Mutable$$serializer_init(internal.BooleanSerializer)), obj.sessions);
    output.endStructure_qatsm0$(this.descriptor);
  };
  StateAllSessions$$serializer.prototype.deserialize_nts5qn$ = function (decoder) {
    var index, readAll = false;
    var bitMask0 = 0;
    var local0
    , local1
    , local2
    , local3;
    var input = decoder.beginStructure_r0sa6z$(this.descriptor, []);
    loopLabel: while (true) {
      index = input.decodeElementIndex_qatsm0$(this.descriptor);
      switch (index) {
        case -2:
          readAll = true;
        case 0:
          local0 = input.decodeStringElement_3zr2iy$(this.descriptor, 0);
          bitMask0 |= 1;
          if (!readAll)
            break;
        case 1:
          local1 = input.decodeIntElement_3zr2iy$(this.descriptor, 1);
          bitMask0 |= 2;
          if (!readAll)
            break;
        case 2:
          local2 = input.decodeBooleanElement_3zr2iy$(this.descriptor, 2);
          bitMask0 |= 4;
          if (!readAll)
            break;
        case 3:
          local3 = (bitMask0 & 8) === 0 ? input.decodeSerializableElement_s44l7r$(this.descriptor, 3, new LinkedHashMapSerializer(StateStatus$$serializer_getInstance(), Mutable$Mutable$$serializer_init(internal.BooleanSerializer))) : input.updateSerializableElement_ehubvl$(this.descriptor, 3, new LinkedHashMapSerializer(StateStatus$$serializer_getInstance(), Mutable$Mutable$$serializer_init(internal.BooleanSerializer)), local3);
          bitMask0 |= 8;
          if (!readAll)
            break;
        case -1:
          break loopLabel;
        default:throw new UnknownFieldException(index);
      }
    }
    input.endStructure_qatsm0$(this.descriptor);
    return StateAllSessions_init(bitMask0, local0, local1, local2, local3, null);
  };
  StateAllSessions$$serializer.prototype.childSerializers = function () {
    return [internal.StringSerializer, internal.IntSerializer, internal.BooleanSerializer, new LinkedHashMapSerializer(StateStatus$$serializer_getInstance(), Mutable$Mutable$$serializer_init(internal.BooleanSerializer))];
  };
  StateAllSessions$$serializer.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: '$serializer',
    interfaces: [GeneratedSerializer]
  };
  var StateAllSessions$$serializer_instance = null;
  function StateAllSessions$$serializer_getInstance() {
    if (StateAllSessions$$serializer_instance === null) {
      new StateAllSessions$$serializer();
    }
    return StateAllSessions$$serializer_instance;
  }
  function StateAllSessions_init(seen1, name, priority, expanded, sessions, serializationConstructorMarker) {
    var $this = serializationConstructorMarker || Object.create(StateAllSessions.prototype);
    $this = StateEvent_init(seen1, name, priority, expanded, $this);
    if ((seen1 & 8) === 0)
      throw new MissingFieldException('sessions');
    else
      $this.sessions = sessions;
    return $this;
  }
  StateAllSessions.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'StateAllSessions',
    interfaces: [StateEvent]
  };
  StateAllSessions.prototype.component1 = function () {
    return this.sessions;
  };
  StateAllSessions.prototype.copy_ph653i$ = function (sessions) {
    return new StateAllSessions(sessions === void 0 ? this.sessions : sessions);
  };
  StateAllSessions.prototype.toString = function () {
    return 'StateAllSessions(sessions=' + Kotlin.toString(this.sessions) + ')';
  };
  StateAllSessions.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.sessions) | 0;
    return result;
  };
  StateAllSessions.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.sessions, other.sessions))));
  };
  function StateStatus(title, priorityy, status, fields, obj) {
    StateStatus$Companion_getInstance();
    if (status === void 0)
      status = null;
    if (fields === void 0)
      fields = HashMap_init();
    if (obj === void 0)
      obj = null;
    StateEvent.call(this, title, priorityy);
    this.title_0 = title;
    this.priorityy_0 = priorityy;
    this.status = status;
    this.fields = fields;
    this.obj = obj;
  }
  StateStatus.prototype.equals = function (other) {
    return Kotlin.isType(other, StateStatus) && equals(this.name, other.name);
  };
  function StateStatus$Companion() {
    StateStatus$Companion_instance = this;
  }
  StateStatus$Companion.prototype.serializer = function () {
    return StateStatus$$serializer_getInstance();
  };
  StateStatus$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var StateStatus$Companion_instance = null;
  function StateStatus$Companion_getInstance() {
    if (StateStatus$Companion_instance === null) {
      new StateStatus$Companion();
    }
    return StateStatus$Companion_instance;
  }
  function StateStatus$$serializer() {
    this.descriptor_dt20b0$_0 = new SerialClassDescImpl('com.mparticle.shared.events.StateStatus', this);
    this.descriptor.addElement_ivxn3r$('name', false);
    this.descriptor.addElement_ivxn3r$('priority', true);
    this.descriptor.addElement_ivxn3r$('expanded', true);
    this.descriptor.addElement_ivxn3r$('title', false);
    this.descriptor.addElement_ivxn3r$('priorityy', false);
    this.descriptor.addElement_ivxn3r$('fields', true);
    StateStatus$$serializer_instance = this;
  }
  Object.defineProperty(StateStatus$$serializer.prototype, 'descriptor', {
    get: function () {
      return this.descriptor_dt20b0$_0;
    }
  });
  StateStatus$$serializer.prototype.serialize_awe97i$ = function (encoder, obj) {
    var output = encoder.beginStructure_r0sa6z$(this.descriptor, []);
    output.encodeStringElement_bgm7zs$(this.descriptor, 0, obj.name);
    if (!equals(obj.priority, 0) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 1))
      output.encodeIntElement_4wpqag$(this.descriptor, 1, obj.priority);
    if (!equals(obj.expanded, false) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 2))
      output.encodeBooleanElement_w1b0nl$(this.descriptor, 2, obj.expanded);
    output.encodeStringElement_bgm7zs$(this.descriptor, 3, obj.title_0);
    output.encodeIntElement_4wpqag$(this.descriptor, 4, obj.priorityy_0);
    if (!equals(obj.fields, HashMap_init()) || output.shouldEncodeElementDefault_3zr2iy$(this.descriptor, 5))
      output.encodeSerializableElement_blecud$(this.descriptor, 5, new LinkedHashMapSerializer(internal.StringSerializer, new PolymorphicSerializer(PrimitiveClasses$anyClass)), obj.fields);
    output.endStructure_qatsm0$(this.descriptor);
  };
  StateStatus$$serializer.prototype.deserialize_nts5qn$ = function (decoder) {
    var index, readAll = false;
    var bitMask0 = 0;
    var local0
    , local1
    , local2
    , local3
    , local4
    , local5;
    var input = decoder.beginStructure_r0sa6z$(this.descriptor, []);
    loopLabel: while (true) {
      index = input.decodeElementIndex_qatsm0$(this.descriptor);
      switch (index) {
        case -2:
          readAll = true;
        case 0:
          local0 = input.decodeStringElement_3zr2iy$(this.descriptor, 0);
          bitMask0 |= 1;
          if (!readAll)
            break;
        case 1:
          local1 = input.decodeIntElement_3zr2iy$(this.descriptor, 1);
          bitMask0 |= 2;
          if (!readAll)
            break;
        case 2:
          local2 = input.decodeBooleanElement_3zr2iy$(this.descriptor, 2);
          bitMask0 |= 4;
          if (!readAll)
            break;
        case 3:
          local3 = input.decodeStringElement_3zr2iy$(this.descriptor, 3);
          bitMask0 |= 8;
          if (!readAll)
            break;
        case 4:
          local4 = input.decodeIntElement_3zr2iy$(this.descriptor, 4);
          bitMask0 |= 16;
          if (!readAll)
            break;
        case 5:
          local5 = (bitMask0 & 32) === 0 ? input.decodeSerializableElement_s44l7r$(this.descriptor, 5, new LinkedHashMapSerializer(internal.StringSerializer, new PolymorphicSerializer(PrimitiveClasses$anyClass))) : input.updateSerializableElement_ehubvl$(this.descriptor, 5, new LinkedHashMapSerializer(internal.StringSerializer, new PolymorphicSerializer(PrimitiveClasses$anyClass)), local5);
          bitMask0 |= 32;
          if (!readAll)
            break;
        case -1:
          break loopLabel;
        default:throw new UnknownFieldException(index);
      }
    }
    input.endStructure_qatsm0$(this.descriptor);
    return StateStatus_init(bitMask0, local0, local1, local2, local3, local4, local5, null);
  };
  StateStatus$$serializer.prototype.childSerializers = function () {
    return [internal.StringSerializer, internal.IntSerializer, internal.BooleanSerializer, internal.StringSerializer, internal.IntSerializer, new LinkedHashMapSerializer(internal.StringSerializer, new PolymorphicSerializer(PrimitiveClasses$anyClass))];
  };
  StateStatus$$serializer.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: '$serializer',
    interfaces: [GeneratedSerializer]
  };
  var StateStatus$$serializer_instance = null;
  function StateStatus$$serializer_getInstance() {
    if (StateStatus$$serializer_instance === null) {
      new StateStatus$$serializer();
    }
    return StateStatus$$serializer_instance;
  }
  function StateStatus_init(seen1, name, priority, expanded, title, priorityy, fields, serializationConstructorMarker) {
    var $this = serializationConstructorMarker || Object.create(StateStatus.prototype);
    $this = StateEvent_init(seen1, name, priority, expanded, $this);
    if ((seen1 & 8) === 0)
      throw new MissingFieldException('title');
    else
      $this.title_0 = title;
    if ((seen1 & 16) === 0)
      throw new MissingFieldException('priorityy');
    else
      $this.priorityy_0 = priorityy;
    if ((seen1 & 32) === 0)
      $this.fields = HashMap_init();
    else
      $this.fields = fields;
    $this.status = null;
    $this.obj = null;
    return $this;
  }
  StateStatus.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'StateStatus',
    interfaces: [StateEvent]
  };
  function Status(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function Status_initFields() {
    Status_initFields = function () {
    };
    Status$Red_instance = new Status('Red', 0);
    Status$Green_instance = new Status('Green', 1);
    Status$Yellow_instance = new Status('Yellow', 2);
    Status$None_instance = new Status('None', 3);
  }
  var Status$Red_instance;
  function Status$Red_getInstance() {
    Status_initFields();
    return Status$Red_instance;
  }
  var Status$Green_instance;
  function Status$Green_getInstance() {
    Status_initFields();
    return Status$Green_instance;
  }
  var Status$Yellow_instance;
  function Status$Yellow_getInstance() {
    Status_initFields();
    return Status$Yellow_instance;
  }
  var Status$None_instance;
  function Status$None_getInstance() {
    Status_initFields();
    return Status$None_instance;
  }
  Status.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Status',
    interfaces: [Enum]
  };
  function Status$values() {
    return [Status$Red_getInstance(), Status$Green_getInstance(), Status$Yellow_getInstance(), Status$None_getInstance()];
  }
  Status.values = Status$values;
  function Status$valueOf(name) {
    switch (name) {
      case 'Red':
        return Status$Red_getInstance();
      case 'Green':
        return Status$Green_getInstance();
      case 'Yellow':
        return Status$Yellow_getInstance();
      case 'None':
        return Status$None_getInstance();
      default:throwISE('No enum constant com.mparticle.shared.events.Status.' + name);
    }
  }
  Status.valueOf_61zpoe$ = Status$valueOf;
  function ChainTitle(name) {
    Event.call(this);
    this.name_jm3bf8$_0 = name;
  }
  Object.defineProperty(ChainTitle.prototype, 'name', {
    get: function () {
      return this.name_jm3bf8$_0;
    }
  });
  ChainTitle.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'ChainTitle',
    interfaces: [Event]
  };
  ChainTitle.prototype.component1 = function () {
    return this.name;
  };
  ChainTitle.prototype.copy_61zpoe$ = function (name) {
    return new ChainTitle(name === void 0 ? this.name : name);
  };
  ChainTitle.prototype.toString = function () {
    return 'ChainTitle(name=' + Kotlin.toString(this.name) + ')';
  };
  ChainTitle.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.name) | 0;
    return result;
  };
  ChainTitle.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.name, other.name))));
  };
  function CategoryTitle(name, itemType, expanded, order) {
    if (expanded === void 0)
      expanded = true;
    if (order === void 0)
      order = Order$Chronological_Recent_First_getInstance();
    Event.call(this);
    this.name_bp9pp5$_0 = name;
    this.itemType = itemType;
    this.expanded = expanded;
    this.order = order;
    this.count = 0;
    this.onExpand_8be2vx$ = null;
  }
  Object.defineProperty(CategoryTitle.prototype, 'name', {
    get: function () {
      return this.name_bp9pp5$_0;
    }
  });
  CategoryTitle.prototype.toggleExpanded = function () {
    var tmp$;
    (tmp$ = this.onExpand_8be2vx$) != null ? tmp$(!this.expanded) : null;
  };
  CategoryTitle.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'CategoryTitle',
    interfaces: [Event]
  };
  function Order(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function Order_initFields() {
    Order_initFields = function () {
    };
    Order$Alphbetical_instance = new Order('Alphbetical', 0);
    Order$Chronological_Recent_First_instance = new Order('Chronological_Recent_First', 1);
    Order$Custom_instance = new Order('Custom', 2);
  }
  var Order$Alphbetical_instance;
  function Order$Alphbetical_getInstance() {
    Order_initFields();
    return Order$Alphbetical_instance;
  }
  var Order$Chronological_Recent_First_instance;
  function Order$Chronological_Recent_First_getInstance() {
    Order_initFields();
    return Order$Chronological_Recent_First_instance;
  }
  var Order$Custom_instance;
  function Order$Custom_getInstance() {
    Order_initFields();
    return Order$Custom_instance;
  }
  Order.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Order',
    interfaces: [Enum]
  };
  function Order$values() {
    return [Order$Alphbetical_getInstance(), Order$Chronological_Recent_First_getInstance(), Order$Custom_getInstance()];
  }
  Order.values = Order$values;
  function Order$valueOf(name) {
    switch (name) {
      case 'Alphbetical':
        return Order$Alphbetical_getInstance();
      case 'Chronological_Recent_First':
        return Order$Chronological_Recent_First_getInstance();
      case 'Custom':
        return Order$Custom_getInstance();
      default:throwISE('No enum constant com.mparticle.shared.events.Order.' + name);
    }
  }
  Order.valueOf_61zpoe$ = Order$valueOf;
  function User(id, userAttributes, userIdentities, cart, consentState, loggedIn, firstSeenTime, lastSeenTime) {
    User$Companion_getInstance();
    this.id = id;
    this.userAttributes_2brtti$_0 = userAttributes;
    this.userIdentities_uajxld$_0 = userIdentities;
    this.cart_vhna20$_0 = cart;
    this.consentState_uux3y7$_0 = consentState;
    this.loggedIn_kp8369$_0 = loggedIn;
    this.firstSeenTime_1r7wu4$_0 = firstSeenTime;
    this.lastSeenTime_rm8b6i$_0 = lastSeenTime;
  }
  Object.defineProperty(User.prototype, 'userAttributes', {
    get: function () {
      return this.userAttributes_2brtti$_0;
    }
  });
  Object.defineProperty(User.prototype, 'userIdentities', {
    get: function () {
      return this.userIdentities_uajxld$_0;
    }
  });
  Object.defineProperty(User.prototype, 'cart', {
    get: function () {
      return this.cart_vhna20$_0;
    }
  });
  Object.defineProperty(User.prototype, 'consentState', {
    get: function () {
      return this.consentState_uux3y7$_0;
    }
  });
  Object.defineProperty(User.prototype, 'loggedIn', {
    get: function () {
      return this.loggedIn_kp8369$_0;
    }
  });
  Object.defineProperty(User.prototype, 'firstSeenTime', {
    get: function () {
      return this.firstSeenTime_1r7wu4$_0;
    }
  });
  Object.defineProperty(User.prototype, 'lastSeenTime', {
    get: function () {
      return this.lastSeenTime_rm8b6i$_0;
    }
  });
  function User$Companion() {
    User$Companion_instance = this;
  }
  User$Companion.prototype.serializer = function () {
    return User$$serializer_getInstance();
  };
  User$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var User$Companion_instance = null;
  function User$Companion_getInstance() {
    if (User$Companion_instance === null) {
      new User$Companion();
    }
    return User$Companion_instance;
  }
  function User$$serializer() {
    this.descriptor_gw5wb3$_0 = new SerialClassDescImpl('com.mparticle.shared.User', this);
    this.descriptor.addElement_ivxn3r$('id', false);
    this.descriptor.addElement_ivxn3r$('userAttributes', false);
    this.descriptor.addElement_ivxn3r$('userIdentities', false);
    this.descriptor.addElement_ivxn3r$('cart', false);
    this.descriptor.addElement_ivxn3r$('consentState', false);
    this.descriptor.addElement_ivxn3r$('loggedIn', false);
    this.descriptor.addElement_ivxn3r$('firstSeenTime', false);
    this.descriptor.addElement_ivxn3r$('lastSeenTime', false);
    User$$serializer_instance = this;
  }
  Object.defineProperty(User$$serializer.prototype, 'descriptor', {
    get: function () {
      return this.descriptor_gw5wb3$_0;
    }
  });
  User$$serializer.prototype.serialize_awe97i$ = function (encoder, obj) {
    var output = encoder.beginStructure_r0sa6z$(this.descriptor, []);
    output.encodeLongElement_a3zgoj$(this.descriptor, 0, obj.id);
    output.encodeSerializableElement_blecud$(this.descriptor, 1, new LinkedHashMapSerializer(internal.StringSerializer, new PolymorphicSerializer(PrimitiveClasses$anyClass)), obj.userAttributes);
    output.encodeSerializableElement_blecud$(this.descriptor, 2, new LinkedHashMapSerializer(internal.StringSerializer, new NullableSerializer(internal.StringSerializer)), obj.userIdentities);
    output.encodeSerializableElement_blecud$(this.descriptor, 3, new LinkedHashMapSerializer(internal.StringSerializer, new NullableSerializer(new PolymorphicSerializer(PrimitiveClasses$anyClass))), obj.cart);
    output.encodeSerializableElement_blecud$(this.descriptor, 4, new LinkedHashMapSerializer(internal.StringSerializer, new NullableSerializer(new PolymorphicSerializer(PrimitiveClasses$anyClass))), obj.consentState);
    output.encodeBooleanElement_w1b0nl$(this.descriptor, 5, obj.loggedIn);
    output.encodeLongElement_a3zgoj$(this.descriptor, 6, obj.firstSeenTime);
    output.encodeLongElement_a3zgoj$(this.descriptor, 7, obj.lastSeenTime);
    output.endStructure_qatsm0$(this.descriptor);
  };
  User$$serializer.prototype.deserialize_nts5qn$ = function (decoder) {
    var index, readAll = false;
    var bitMask0 = 0;
    var local0
    , local1
    , local2
    , local3
    , local4
    , local5
    , local6
    , local7;
    var input = decoder.beginStructure_r0sa6z$(this.descriptor, []);
    loopLabel: while (true) {
      index = input.decodeElementIndex_qatsm0$(this.descriptor);
      switch (index) {
        case -2:
          readAll = true;
        case 0:
          local0 = input.decodeLongElement_3zr2iy$(this.descriptor, 0);
          bitMask0 |= 1;
          if (!readAll)
            break;
        case 1:
          local1 = (bitMask0 & 2) === 0 ? input.decodeSerializableElement_s44l7r$(this.descriptor, 1, new LinkedHashMapSerializer(internal.StringSerializer, new PolymorphicSerializer(PrimitiveClasses$anyClass))) : input.updateSerializableElement_ehubvl$(this.descriptor, 1, new LinkedHashMapSerializer(internal.StringSerializer, new PolymorphicSerializer(PrimitiveClasses$anyClass)), local1);
          bitMask0 |= 2;
          if (!readAll)
            break;
        case 2:
          local2 = (bitMask0 & 4) === 0 ? input.decodeSerializableElement_s44l7r$(this.descriptor, 2, new LinkedHashMapSerializer(internal.StringSerializer, new NullableSerializer(internal.StringSerializer))) : input.updateSerializableElement_ehubvl$(this.descriptor, 2, new LinkedHashMapSerializer(internal.StringSerializer, new NullableSerializer(internal.StringSerializer)), local2);
          bitMask0 |= 4;
          if (!readAll)
            break;
        case 3:
          local3 = (bitMask0 & 8) === 0 ? input.decodeSerializableElement_s44l7r$(this.descriptor, 3, new LinkedHashMapSerializer(internal.StringSerializer, new NullableSerializer(new PolymorphicSerializer(PrimitiveClasses$anyClass)))) : input.updateSerializableElement_ehubvl$(this.descriptor, 3, new LinkedHashMapSerializer(internal.StringSerializer, new NullableSerializer(new PolymorphicSerializer(PrimitiveClasses$anyClass))), local3);
          bitMask0 |= 8;
          if (!readAll)
            break;
        case 4:
          local4 = (bitMask0 & 16) === 0 ? input.decodeSerializableElement_s44l7r$(this.descriptor, 4, new LinkedHashMapSerializer(internal.StringSerializer, new NullableSerializer(new PolymorphicSerializer(PrimitiveClasses$anyClass)))) : input.updateSerializableElement_ehubvl$(this.descriptor, 4, new LinkedHashMapSerializer(internal.StringSerializer, new NullableSerializer(new PolymorphicSerializer(PrimitiveClasses$anyClass))), local4);
          bitMask0 |= 16;
          if (!readAll)
            break;
        case 5:
          local5 = input.decodeBooleanElement_3zr2iy$(this.descriptor, 5);
          bitMask0 |= 32;
          if (!readAll)
            break;
        case 6:
          local6 = input.decodeLongElement_3zr2iy$(this.descriptor, 6);
          bitMask0 |= 64;
          if (!readAll)
            break;
        case 7:
          local7 = input.decodeLongElement_3zr2iy$(this.descriptor, 7);
          bitMask0 |= 128;
          if (!readAll)
            break;
        case -1:
          break loopLabel;
        default:throw new UnknownFieldException(index);
      }
    }
    input.endStructure_qatsm0$(this.descriptor);
    return User_init(bitMask0, local0, local1, local2, local3, local4, local5, local6, local7, null);
  };
  User$$serializer.prototype.childSerializers = function () {
    return [internal.LongSerializer, new LinkedHashMapSerializer(internal.StringSerializer, new PolymorphicSerializer(PrimitiveClasses$anyClass)), new LinkedHashMapSerializer(internal.StringSerializer, new NullableSerializer(internal.StringSerializer)), new LinkedHashMapSerializer(internal.StringSerializer, new NullableSerializer(new PolymorphicSerializer(PrimitiveClasses$anyClass))), new LinkedHashMapSerializer(internal.StringSerializer, new NullableSerializer(new PolymorphicSerializer(PrimitiveClasses$anyClass))), internal.BooleanSerializer, internal.LongSerializer, internal.LongSerializer];
  };
  User$$serializer.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: '$serializer',
    interfaces: [GeneratedSerializer]
  };
  var User$$serializer_instance = null;
  function User$$serializer_getInstance() {
    if (User$$serializer_instance === null) {
      new User$$serializer();
    }
    return User$$serializer_instance;
  }
  function User_init(seen1, id, userAttributes, userIdentities, cart, consentState, loggedIn, firstSeenTime, lastSeenTime, serializationConstructorMarker) {
    var $this = serializationConstructorMarker || Object.create(User.prototype);
    if ((seen1 & 1) === 0)
      throw new MissingFieldException('id');
    else
      $this.id = id;
    if ((seen1 & 2) === 0)
      throw new MissingFieldException('userAttributes');
    else
      $this.userAttributes_2brtti$_0 = userAttributes;
    if ((seen1 & 4) === 0)
      throw new MissingFieldException('userIdentities');
    else
      $this.userIdentities_uajxld$_0 = userIdentities;
    if ((seen1 & 8) === 0)
      throw new MissingFieldException('cart');
    else
      $this.cart_vhna20$_0 = cart;
    if ((seen1 & 16) === 0)
      throw new MissingFieldException('consentState');
    else
      $this.consentState_uux3y7$_0 = consentState;
    if ((seen1 & 32) === 0)
      throw new MissingFieldException('loggedIn');
    else
      $this.loggedIn_kp8369$_0 = loggedIn;
    if ((seen1 & 64) === 0)
      throw new MissingFieldException('firstSeenTime');
    else
      $this.firstSeenTime_1r7wu4$_0 = firstSeenTime;
    if ((seen1 & 128) === 0)
      throw new MissingFieldException('lastSeenTime');
    else
      $this.lastSeenTime_rm8b6i$_0 = lastSeenTime;
    return $this;
  }
  User.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'User',
    interfaces: []
  };
  function Mutable(value) {
    Mutable$Companion_getInstance();
    this.value = value;
  }
  function Mutable$Companion() {
    Mutable$Companion_instance = this;
  }
  Mutable$Companion.prototype.serializer_swdriu$ = function (typeSerial0) {
    return Mutable$Mutable$$serializer_init(typeSerial0);
  };
  Mutable$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var Mutable$Companion_instance = null;
  function Mutable$Companion_getInstance() {
    if (Mutable$Companion_instance === null) {
      new Mutable$Companion();
    }
    return Mutable$Companion_instance;
  }
  function Mutable$$serializer() {
    this.descriptor_rky9ub$_0 = new SerialClassDescImpl('com.mparticle.shared.utils.Mutable', this);
    this.descriptor.addElement_ivxn3r$('value', false);
  }
  Object.defineProperty(Mutable$$serializer.prototype, 'descriptor', {
    get: function () {
      return this.descriptor_rky9ub$_0;
    }
  });
  Mutable$$serializer.prototype.serialize_awe97i$ = function (encoder, obj) {
    var output = encoder.beginStructure_r0sa6z$(this.descriptor, [this.typeSerial0]);
    output.encodeSerializableElement_blecud$(this.descriptor, 0, this.typeSerial0, obj.value);
    output.endStructure_qatsm0$(this.descriptor);
  };
  Mutable$$serializer.prototype.deserialize_nts5qn$ = function (decoder) {
    var index, readAll = false;
    var bitMask0 = 0;
    var local0;
    var input = decoder.beginStructure_r0sa6z$(this.descriptor, [this.typeSerial0]);
    loopLabel: while (true) {
      index = input.decodeElementIndex_qatsm0$(this.descriptor);
      switch (index) {
        case -2:
          readAll = true;
        case 0:
          local0 = (bitMask0 & 1) === 0 ? input.decodeSerializableElement_s44l7r$(this.descriptor, 0, this.typeSerial0) : input.updateSerializableElement_ehubvl$(this.descriptor, 0, this.typeSerial0, local0);
          bitMask0 |= 1;
          if (!readAll)
            break;
        case -1:
          break loopLabel;
        default:throw new UnknownFieldException(index);
      }
    }
    input.endStructure_qatsm0$(this.descriptor);
    return Mutable_init(bitMask0, local0, null);
  };
  Mutable$$serializer.prototype.childSerializers = function () {
    return [this.typeSerial0];
  };
  function Mutable$Mutable$$serializer_init(typeSerial0) {
    var $this = new Mutable$$serializer();
    $this.typeSerial0 = typeSerial0;
    return $this;
  }
  Mutable$$serializer.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: '$serializer',
    interfaces: [GeneratedSerializer]
  };
  function Mutable_init(seen1, value, serializationConstructorMarker) {
    var $this = serializationConstructorMarker || Object.create(Mutable.prototype);
    if ((seen1 & 1) === 0)
      throw new MissingFieldException('value');
    else
      $this.value = value;
    return $this;
  }
  Mutable.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Mutable',
    interfaces: []
  };
  Mutable.prototype.component1 = function () {
    return this.value;
  };
  Mutable.prototype.copy_11rb$ = function (value) {
    return new Mutable(value === void 0 ? this.value : value);
  };
  Mutable.prototype.toString = function () {
    return 'Mutable(value=' + Kotlin.toString(this.value) + ')';
  };
  Mutable.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.value) | 0;
    return result;
  };
  Mutable.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.value, other.value))));
  };
  function PlatformApis() {
  }
  PlatformApis.prototype.getTimestamp = function () {
    return Kotlin.Long.fromNumber((new Date()).getTime());
  };
  PlatformApis.prototype.print_61zpoe$ = function (message) {
    console.log(message);
  };
  PlatformApis.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'PlatformApis',
    interfaces: []
  };
  Object.defineProperty(EventViewType, 'valTitle', {
    get: EventViewType$valTitle_getInstance
  });
  Object.defineProperty(EventViewType, 'valMessage', {
    get: EventViewType$valMessage_getInstance
  });
  Object.defineProperty(EventViewType, 'valMessageTable', {
    get: EventViewType$valMessageTable_getInstance
  });
  Object.defineProperty(EventViewType, 'valNetworkRequest', {
    get: EventViewType$valNetworkRequest_getInstance
  });
  Object.defineProperty(EventViewType, 'valApiCall', {
    get: EventViewType$valApiCall_getInstance
  });
  Object.defineProperty(EventViewType, 'valKit', {
    get: EventViewType$valKit_getInstance
  });
  Object.defineProperty(EventViewType, 'valStateGeneric', {
    get: EventViewType$valStateGeneric_getInstance
  });
  Object.defineProperty(EventViewType, 'valStateCurrentUser', {
    get: EventViewType$valStateCurrentUser_getInstance
  });
  Object.defineProperty(EventViewType, 'valStateList', {
    get: EventViewType$valStateList_getInstance
  });
  Object.defineProperty(EventViewType, 'valStateStatus', {
    get: EventViewType$valStateStatus_getInstance
  });
  Object.defineProperty(EventViewType, 'valChainTitle', {
    get: EventViewType$valChainTitle_getInstance
  });
  Object.defineProperty(EventViewType, 'valNext', {
    get: EventViewType$valNext_getInstance
  });
  Object.defineProperty(EventViewType, 'valUnknown', {
    get: EventViewType$valUnknown_getInstance
  });
  var package$com = _.com || (_.com = {});
  var package$mparticle = package$com.mparticle || (package$com.mparticle = {});
  var package$shared = package$mparticle.shared || (package$mparticle.shared = {});
  package$shared.EventViewType = EventViewType;
  package$shared.getDtoType_7unwzt$ = getDtoType;
  package$shared.getShortName_7unwzt$ = getShortName;
  package$shared.putIfEmpty_6y9eq4$ = putIfEmpty;
  package$shared.Serializer = Serializer;
  Object.defineProperty(ViewControllerManager, 'Companion', {
    get: ViewControllerManager$Companion_getInstance
  });
  package$shared.ViewControllerManager = ViewControllerManager;
  var package$controllers = package$shared.controllers || (package$shared.controllers = {});
  package$controllers.BaseController = BaseController;
  package$controllers.CategoryController = CategoryController;
  package$controllers.StreamController = StreamController;
  Object.defineProperty(ApiCall, 'Companion', {
    get: ApiCall$Companion_getInstance
  });
  Object.defineProperty(ApiCall, '$serializer', {
    get: ApiCall$$serializer_getInstance
  });
  var package$events = package$shared.events || (package$shared.events = {});
  package$events.ApiCall_init_9n7ii8$ = ApiCall_init;
  package$events.ApiCall = ApiCall;
  Object.defineProperty(KitApiCall, 'Companion', {
    get: KitApiCall$Companion_getInstance
  });
  Object.defineProperty(KitApiCall, '$serializer', {
    get: KitApiCall$$serializer_getInstance
  });
  package$events.KitApiCall_init_pywkso$ = KitApiCall_init;
  package$events.KitApiCall = KitApiCall;
  Object.defineProperty(ObjectArgument, 'Companion', {
    get: ObjectArgument$Companion_getInstance
  });
  Object.defineProperty(ObjectArgument, '$serializer', {
    get: ObjectArgument$$serializer_getInstance
  });
  package$events.ObjectArgument_init_25f158$ = ObjectArgument_init;
  package$events.ObjectArgument = ObjectArgument;
  package$events.ObjectValue = ObjectValue;
  Object.defineProperty(NullObject, 'Companion', {
    get: NullObject$Companion_getInstance
  });
  Object.defineProperty(NullObject, '$serializer', {
    get: NullObject$$serializer_getInstance
  });
  package$events.NullObject_init_a66qd8$ = NullObject_init;
  package$events.NullObject = NullObject;
  Object.defineProperty(Primitive, 'Companion', {
    get: Primitive$Companion_getInstance
  });
  Object.defineProperty(Primitive, '$serializer', {
    get: Primitive$$serializer_getInstance
  });
  package$events.Primitive_init_d69rk$ = Primitive_init;
  package$events.Primitive = Primitive;
  Object.defineProperty(EnumObject, 'Companion', {
    get: EnumObject$Companion_getInstance
  });
  Object.defineProperty(EnumObject, '$serializer', {
    get: EnumObject$$serializer_getInstance
  });
  package$events.EnumObject_init_lmlwo5$ = EnumObject_init;
  package$events.EnumObject = EnumObject;
  Object.defineProperty(Obj, 'Companion', {
    get: Obj$Companion_getInstance
  });
  Object.defineProperty(Obj, '$serializer', {
    get: Obj$$serializer_getInstance
  });
  package$events.Obj_init_vfu1f8$ = Obj_init;
  package$events.Obj = Obj;
  Object.defineProperty(CollectionObject, 'Companion', {
    get: CollectionObject$Companion_getInstance
  });
  Object.defineProperty(CollectionObject, '$serializer', {
    get: CollectionObject$$serializer_getInstance
  });
  package$events.CollectionObject_init_nnu1t8$ = CollectionObject_init;
  package$events.CollectionObject = CollectionObject;
  Object.defineProperty(MapObject, 'Companion', {
    get: MapObject$Companion_getInstance
  });
  Object.defineProperty(MapObject, '$serializer', {
    get: MapObject$$serializer_getInstance
  });
  package$events.MapObject_init_b3gei$ = MapObject_init;
  package$events.MapObject = MapObject;
  Object.defineProperty(FieldObject, 'Companion', {
    get: FieldObject$Companion_getInstance
  });
  Object.defineProperty(FieldObject, '$serializer', {
    get: FieldObject$$serializer_getInstance
  });
  package$events.FieldObject_init_j8qtul$ = FieldObject_init;
  package$events.FieldObject = FieldObject;
  Object.defineProperty(EventCollection, 'Companion', {
    get: EventCollection$Companion_getInstance
  });
  Object.defineProperty(EventCollection, '$serializer', {
    get: EventCollection$$serializer_getInstance
  });
  package$events.EventCollection_init_rud8mw$ = EventCollection_init;
  package$events.EventCollection = EventCollection;
  package$events.Event = Event;
  package$events.ChainableEvent = ChainableEvent;
  Object.defineProperty(Kit, 'Companion', {
    get: Kit$Companion_getInstance
  });
  Object.defineProperty(Kit, '$serializer', {
    get: Kit$$serializer_getInstance
  });
  package$events.Kit_init_1t1qkz$ = Kit_init;
  package$events.Kit = Kit;
  Object.defineProperty(MessageTable, 'Companion', {
    get: MessageTable$Companion_getInstance
  });
  Object.defineProperty(MessageTable, '$serializer', {
    get: MessageTable$$serializer_getInstance
  });
  package$events.MessageTable_init_xr1xir$ = MessageTable_init;
  package$events.MessageTable = MessageTable;
  Object.defineProperty(MessageEvent, 'Companion', {
    get: MessageEvent$Companion_getInstance
  });
  Object.defineProperty(MessageEvent, '$serializer', {
    get: MessageEvent$$serializer_getInstance
  });
  package$events.MessageEvent_init_agl3bb$ = MessageEvent_init;
  package$events.MessageEvent = MessageEvent;
  Object.defineProperty(NetworkRequest, 'Companion', {
    get: NetworkRequest$Companion_getInstance
  });
  Object.defineProperty(NetworkRequest, '$serializer', {
    get: NetworkRequest$$serializer_getInstance
  });
  package$events.NetworkRequest_init_m5sm6j$ = NetworkRequest_init;
  package$events.NetworkRequest = NetworkRequest;
  Object.defineProperty(StateEvent, 'Companion', {
    get: StateEvent$Companion_getInstance
  });
  Object.defineProperty(StateEvent, '$serializer', {
    get: StateEvent$$serializer_getInstance
  });
  package$events.StateEvent_init_pv04pu$ = StateEvent_init;
  package$events.StateEvent = StateEvent;
  Object.defineProperty(StateCurrentUser, 'Companion', {
    get: StateCurrentUser$Companion_getInstance
  });
  Object.defineProperty(StateCurrentUser, '$serializer', {
    get: StateCurrentUser$$serializer_getInstance
  });
  package$events.StateCurrentUser_init_e36m45$ = StateCurrentUser_init;
  package$events.StateCurrentUser = StateCurrentUser;
  Object.defineProperty(StateAllUsers, 'Companion', {
    get: StateAllUsers$Companion_getInstance
  });
  Object.defineProperty(StateAllUsers, '$serializer', {
    get: StateAllUsers$$serializer_getInstance
  });
  package$events.StateAllUsers_init_nn5bqh$ = StateAllUsers_init;
  package$events.StateAllUsers = StateAllUsers;
  Object.defineProperty(StateAllSessions, 'Companion', {
    get: StateAllSessions$Companion_getInstance
  });
  Object.defineProperty(StateAllSessions, '$serializer', {
    get: StateAllSessions$$serializer_getInstance
  });
  package$events.StateAllSessions_init_rhfxv5$ = StateAllSessions_init;
  package$events.StateAllSessions = StateAllSessions;
  Object.defineProperty(StateStatus, 'Companion', {
    get: StateStatus$Companion_getInstance
  });
  Object.defineProperty(StateStatus, '$serializer', {
    get: StateStatus$$serializer_getInstance
  });
  package$events.StateStatus_init_h99y48$ = StateStatus_init;
  package$events.StateStatus = StateStatus;
  Object.defineProperty(Status, 'Red', {
    get: Status$Red_getInstance
  });
  Object.defineProperty(Status, 'Green', {
    get: Status$Green_getInstance
  });
  Object.defineProperty(Status, 'Yellow', {
    get: Status$Yellow_getInstance
  });
  Object.defineProperty(Status, 'None', {
    get: Status$None_getInstance
  });
  package$events.Status = Status;
  package$events.ChainTitle = ChainTitle;
  package$events.CategoryTitle = CategoryTitle;
  Object.defineProperty(Order, 'Alphbetical', {
    get: Order$Alphbetical_getInstance
  });
  Object.defineProperty(Order, 'Chronological_Recent_First', {
    get: Order$Chronological_Recent_First_getInstance
  });
  Object.defineProperty(Order, 'Custom', {
    get: Order$Custom_getInstance
  });
  package$events.Order = Order;
  Object.defineProperty(User, 'Companion', {
    get: User$Companion_getInstance
  });
  Object.defineProperty(User, '$serializer', {
    get: User$$serializer_getInstance
  });
  package$shared.User_init_as3jqa$ = User_init;
  package$shared.User = User;
  Object.defineProperty(Mutable, 'Companion', {
    get: Mutable$Companion_getInstance
  });
  Mutable.$serializer = Mutable$$serializer;
  var package$utils = package$shared.utils || (package$shared.utils = {});
  package$utils.Mutable_init_f1g3hc$ = Mutable_init;
  package$utils.Mutable = Mutable;
  package$shared.PlatformApis = PlatformApis;
  ApiCall$$serializer.prototype.patch_mynpiu$ = GeneratedSerializer.prototype.patch_mynpiu$;
  KitApiCall$$serializer.prototype.patch_mynpiu$ = GeneratedSerializer.prototype.patch_mynpiu$;
  ObjectArgument$$serializer.prototype.patch_mynpiu$ = GeneratedSerializer.prototype.patch_mynpiu$;
  NullObject$$serializer.prototype.patch_mynpiu$ = GeneratedSerializer.prototype.patch_mynpiu$;
  Primitive$$serializer.prototype.patch_mynpiu$ = GeneratedSerializer.prototype.patch_mynpiu$;
  EnumObject$$serializer.prototype.patch_mynpiu$ = GeneratedSerializer.prototype.patch_mynpiu$;
  Obj$$serializer.prototype.patch_mynpiu$ = GeneratedSerializer.prototype.patch_mynpiu$;
  CollectionObject$$serializer.prototype.patch_mynpiu$ = GeneratedSerializer.prototype.patch_mynpiu$;
  MapObject$$serializer.prototype.patch_mynpiu$ = GeneratedSerializer.prototype.patch_mynpiu$;
  FieldObject$$serializer.prototype.patch_mynpiu$ = GeneratedSerializer.prototype.patch_mynpiu$;
  EventCollection$$serializer.prototype.patch_mynpiu$ = GeneratedSerializer.prototype.patch_mynpiu$;
  Kit$$serializer.prototype.patch_mynpiu$ = GeneratedSerializer.prototype.patch_mynpiu$;
  MessageTable$$serializer.prototype.patch_mynpiu$ = GeneratedSerializer.prototype.patch_mynpiu$;
  MessageEvent$$serializer.prototype.patch_mynpiu$ = GeneratedSerializer.prototype.patch_mynpiu$;
  NetworkRequest$$serializer.prototype.patch_mynpiu$ = GeneratedSerializer.prototype.patch_mynpiu$;
  StateEvent$$serializer.prototype.patch_mynpiu$ = GeneratedSerializer.prototype.patch_mynpiu$;
  StateCurrentUser$$serializer.prototype.patch_mynpiu$ = GeneratedSerializer.prototype.patch_mynpiu$;
  StateAllUsers$$serializer.prototype.patch_mynpiu$ = GeneratedSerializer.prototype.patch_mynpiu$;
  StateAllSessions$$serializer.prototype.patch_mynpiu$ = GeneratedSerializer.prototype.patch_mynpiu$;
  StateStatus$$serializer.prototype.patch_mynpiu$ = GeneratedSerializer.prototype.patch_mynpiu$;
  User$$serializer.prototype.patch_mynpiu$ = GeneratedSerializer.prototype.patch_mynpiu$;
  Mutable$$serializer.prototype.patch_mynpiu$ = GeneratedSerializer.prototype.patch_mynpiu$;
  Kotlin.defineModule('shared', _);
  return _;
}));

//# sourceMappingURL=shared.js.map
