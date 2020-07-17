package com.omaraboesmail.bargain.data

import com.google.firebase.firestore.DocumentSnapshot

interface EntityMapper<Entity, DomainModel> {

    fun mapFromEntity(entity: DocumentSnapshot): DomainModel

    fun mapToEntity(domainModel: DomainModel): Entity
}