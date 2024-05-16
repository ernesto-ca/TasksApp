package ec.project.todos.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ec.project.todos.data.TaskDao
import ec.project.todos.data.repository.ITodoRepository
import ec.project.todos.data.repository.TodoRepositoryImp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun providesTodoRepository(taskDao: TaskDao): ITodoRepository {
        return TodoRepositoryImp(taskDao)
    }
}