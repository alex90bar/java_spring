package com.example.MyBookShopApp.config;

import com.example.MyBookShopApp.data.BookRepository;
import com.example.MyBookShopApp.data.TestEntity;
import com.example.MyBookShopApp.data.TestEntityCrudRepository;
import com.example.MyBookShopApp.data.TestEntityDao;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class CommandLineRunnerImpl implements CommandLineRunner {

//  EntityManagerFactory entityManagerFactory;
//  TestEntityDao testEntityDao;

  TestEntityCrudRepository testEntityCrudRepository;
  BookRepository bookRepository;

  @Autowired
  public CommandLineRunnerImpl(
      TestEntityCrudRepository testEntityCrudRepository,
      BookRepository bookRepository) {
    this.testEntityCrudRepository = testEntityCrudRepository;
    this.bookRepository = bookRepository;
  }

//  @Autowired
//  public CommandLineRunnerImpl(EntityManagerFactory entityManagerFactory,
//      TestEntityDao testEntityDao) {
//    this.entityManagerFactory = entityManagerFactory;
//    this.testEntityDao = testEntityDao;
//  }

  @Override
  public void run(String... args) throws Exception {
    for (int i=0; i<5; i++){
      createTestEntity(new TestEntity());
    }

    TestEntity readTestEntity =  readTestEntityById(3L); //testEntityDao.findOne(3L); - через AbstractHibernateDao
    if (readTestEntity != null){
      Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info("read " + readTestEntity.toString());
    } else {
      throw new NullPointerException();
    }
    
    TestEntity updatedTestEntity = updateTestEntityById(5L);
    if (updatedTestEntity != null){
      Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info("update " + updatedTestEntity.toString());
    } else {
      throw new NullPointerException();
    }

    //deleteTestEntityById(4L);

//    Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName())
//        .info(bookRepository.findBooksByAuthor_FirstName("Kimberli").toString());
//
//    Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName())
//        .info(bookRepository.customFindAllBooks().toString());

  }

  private void deleteTestEntityById(Long id) {
    TestEntity testEntity = testEntityCrudRepository.findById(id).get();
    testEntityCrudRepository.delete(testEntity);

//    Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
//    Transaction tx = null;
//
//    try {
//      tx = session.beginTransaction();
//      TestEntity findEntity = readTestEntityById(id);
//      TestEntity mergedTestEntity = (TestEntity) session.merge(findEntity);
//      session.remove(mergedTestEntity);
//      tx.commit();
//    } catch (HibernateException hex) {
//      if (tx != null) {
//        tx.rollback();
//      } else {
//        hex.printStackTrace();
//      }
//    }finally {
//      session.close();
//    }
  }

  //Обновление данных
  private TestEntity updateTestEntityById(Long id) {
    TestEntity testEntity = testEntityCrudRepository.findById(id).get();
    testEntity.setData("NEW DATA");
    testEntityCrudRepository.save(testEntity);
    return testEntity;

//    Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
//    Transaction tx = null;
//    TestEntity result = null;
//
//    try {
//      tx = session.beginTransaction();
//      TestEntity findEntity = readTestEntityById(id);
//      findEntity.setData("NEW DATA UPDATE");
//      result = (TestEntity) session.merge(findEntity);
//      tx.commit();
//    } catch (HibernateException hex) {
//      if (tx != null) {
//        tx.rollback();
//      } else {
//        hex.printStackTrace();
//      }
//    }finally {
//      session.close();
//    }
//    return result;
  }

//  Чтение данных
  private TestEntity readTestEntityById(Long id) {
    return testEntityCrudRepository.findById(id).get();

//    Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
//    Transaction tx = null;
//    TestEntity result = null;
//
//    try {
//      tx = session.beginTransaction();
//      result = session.find(TestEntity.class, id);
//      tx.commit();
//    } catch (HibernateException hex) {
//      if (tx != null) {
//        tx.rollback();
//      } else {
//        hex.printStackTrace();
//      }
//    }finally {
//      session.close();
//    }
//    return result;
  }

  //Создание тестовых данных
  private void createTestEntity(TestEntity entity) {
    entity.setData(entity.getClass().getSimpleName() + entity.hashCode());
    testEntityCrudRepository.save(entity);

//    Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
//    Transaction tx = null;
//
//    try {
//      tx = session.beginTransaction();
//      entity.setData(entity.getClass().getSimpleName() + entity.hashCode());
//      session.save(entity);
//      tx.commit();
//    } catch (HibernateException hex) {
//      if (tx != null) {
//        tx.rollback();
//      } else {
//        hex.printStackTrace();
//      }
//    }finally {
//      session.close();
//    }
  }
}
